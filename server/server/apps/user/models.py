from django.db import models
from django.contrib.auth.models import AbstractUser


# Create your models here.

class User(AbstractUser):
    dni = models.CharField(max_length=8, unique=True, null=False)
    uuid = models.CharField(max_length=32, unique=True, null=False)

