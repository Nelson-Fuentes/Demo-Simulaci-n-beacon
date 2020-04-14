import requests
import re
from server.apps.user.models import User
import uuid

URL = 'http://redecodifica.com/reniec/consulta_reniec.php'


class ReniecService:

    def generateUUID(self):
        uuid_ = uuid.uuid1()
        while(len(User.objects.filter(uuid=uuid_))!=0):
            uuid_ = uuid.uuid1()
        return uuid_

    def searchUser(self, dni):
         response = requests.post(URL, {'dni': dni})
         arr = re.split('\[(.+)\]', response.text.split('\n').pop().replace('"', ''))[1].title().split(',')
         if arr[1] == 'Null' or arr[2] == 'Null' or arr[3] == 'Null':
            return None
         else:
             user = User()
             user.first_name=arr[1]
             user.last_name=arr[2] + " " + arr[3]
             user.dni = dni
             user.username = dni
             user.uuid = self.generateUUID()
             return user

    def getUser(self, dni):
        try:
            return User.objects.get(dni=dni)
        except:
            return self.searchUser(dni)