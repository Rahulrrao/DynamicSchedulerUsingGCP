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

import json

from aspaas import get_model, tasks
from flask import Blueprint, current_app, redirect, render_template, request, \
    session, url_for, jsonify
from flask import Response


crud = Blueprint('crud', __name__)

@crud.route('/')
def index():
    return 'Hello World!'

@crud.route('/<id>/')
def view(id):
    program = get_model().read(id)
    return jsonify(program)

@crud.route('/<id>/status')
def view_status(id):
    program = get_model().read(id)
    status = program['status']
    if request.args.get('format', 'json').lower() == 'json':
        return jsonify({'id': id, 'status': status })
    return Response(status, mimetype='text/plain')

@crud.route('/<id>/output')
def view_output(id):
    program = get_model().read(id)
    output = program.get('output')
    return Response(output, mimetype='text/plain')

@crud.route('/<id>/result')
def view_result(id):
    program = get_model().read(id)
    result = program.get('result')
    result['id'] = id
    return jsonify(result)

@crud.route('/run', methods=['POST'])
def run():
    if request.method == 'POST':
        file = request.files['code']
        data = {}
        if file:
            data['code'] = file.read().decode('utf-8')
            file.close()
        data['status'] = 'pending'
        program = get_model().create(data)

        tasks.create_clingo_job(program['id'])

        if request.args.get('format', 'json').lower() == 'json':
            return jsonify({'id': program['id']})
        return Response(str(program['id']), mimetype='text/plain')
    return 'POST only'
