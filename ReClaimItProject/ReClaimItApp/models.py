from django.db import models
from rest_framework import serializers

# Create your models here.
class List(models.Model):
    name = models.CharField(max_length=100)
    age = models.IntegerField()
    dob = models.DateField()
    gender = models.CharField(max_length=10)
    email = models.EmailField()
    address = models.TextField(max_length=500)
    phone = models.BigIntegerField()
    list = models.TextField(default="None")

class ListSerializer(serializers.ModelSerializer):
    class Meta:
        model = List
        fields = "__all__"