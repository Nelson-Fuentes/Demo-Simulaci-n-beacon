from rest_framework.response import Response
from rest_framework.views import APIView

from server.apps.user.models import User
from server.apps.user.serializers import UserSerializer
from webScrapping.dni_scrapper import ReniecService


class UserAPIView(APIView):
    serializer_class = UserSerializer

    def get(self, request, *arg, **kwargs):
        pk = self.kwargs.get('pk')
        user = ReniecService().getUser(pk)
        if user is not None:
            user.save()
            return Response(self.serializer_class(user).data)
        else:
            response = Response(self.serializer_class(user).data)
            return response