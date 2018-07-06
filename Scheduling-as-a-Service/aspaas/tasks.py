# Copyright 2015 Google Inc.
#
# Licensed under the Apache License, Version 2.0 (the "License");
# you may not use this file except in compliance with the License.
# You may obtain a copy of the License at
#
#     http://www.apache.org/licenses/LICENSE-2.0
#
# Unless required by applicable law or agreed to in writing, software
# distributed under the License is distributed on an "AS IS" BASIS,
# WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
# See the License for the specific language governing permissions and
# limitations under the License.

import logging

from aspaas import get_model
from flask import current_app
from google.cloud import pubsub
import psq

import re
import os
import tempfile
import subprocess


publisher_client = pubsub.PublisherClient()
subscriber_client = pubsub.SubscriberClient()

def create_clingo_job(prog_id):
    from kubernetes import config
    config.load_incluster_config()
    from kubernetes import client
    api_instance = client.BatchV1Api()
    namespace = 'default'
    body = client.V1Job(
        api_version='batch/v1',
        kind='Job',
        metadata=client.V1ObjectMeta(
            name='job-{}'.format(prog_id),
        ),
        spec=client.V1JobSpec(
            template=client.V1PodTemplateSpec(
                metadata=client.V1ObjectMeta(
                    name='job-{}'.format(prog_id),
                ),
                spec=client.V1PodSpec(
                    restart_policy='Never',
                    containers=[
                        client.V1Container(
                            name='aspaas-app',
                            image='gcr.io/{}/worker'.format(current_app.config['PROJECT_ID']),
                            command=["python"],
                            args=["run.py", str(prog_id)],
                        )
                    ],
                ),
            ),
        ),
    )
    api_instance.create_namespaced_job(namespace, body)

def delete_job(prog_id):
    from kubernetes import config
    config.load_incluster_config()
    from kubernetes import client
    api_instance = client.BatchV1Api()
    namespace = 'default'
    body = client.V1DeleteOptions(
        api_version='batch/v1',
        kind='DeleteOptions',
        propagation_policy='Background',
        grace_period_seconds=3,
    )
    api_instance.delete_namespaced_job(
        'job-{}'.format(prog_id),
        namespace,
        body,
        grace_period_seconds=3,
        propagation_policy='Background',
    )

def parse_output(output):
    result = {}
    lines = (l for l in output.splitlines())

    # clingo version 4.5.4
    result['compiler'] = next(lines)

    # Reading from ...
    next(lines)

    # - Solving...
    # - UNKNOWN
    # - UNSATISFIABLE
    line = next(lines)
    if 'Solving' in line:
        # Answer: 1
        line = next(lines)
        if 'UNSATISFIABLE' in line:
            result['satisfiable'] = False
        else:
            answer_count = max(0, int(re.match('\s*Answer\s*:\s*(\d+)', line).group(1)))
            result['answers'] = next(lines).split()
            result['satisfiable'] = ('SATISFIABLE' in next(lines))
    else:
        result['satisfiable'] = None

    next(lines)
    result['models'] = re.match('\s*Models\s*:\s*(\d+\+?)', next(lines)).group(1)
    return result


def execute_clingo(prog_id):
    model = get_model()
    program = model.read(prog_id)
    program['status'] = 'running'
    model.update(program, prog_id)

    with tempfile.TemporaryDirectory() as tmpdirname:
        programfile = os.path.abspath(os.path.join(tmpdirname, 'code'))
        with open(programfile, 'w') as fh:
            fh.write(program['code'])

        result = subprocess.Popen(['clingo', programfile], stdout=subprocess.PIPE, universal_newlines=True)
        (stdout, stderr) = result.communicate()

        program['status'] = 'done'
        program['output'] = stdout
        program['result'] = parse_output(stdout)

        model.update(program, prog_id)
        delete_job(prog_id)
