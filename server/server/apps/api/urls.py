from django.urls import path
from .views import UserAPIView

urlpatterns = [
    path('user/<pk>', UserAPIView.as_view(), name="User-API"),

]
