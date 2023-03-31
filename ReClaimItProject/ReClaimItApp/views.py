# views.py
import pyrebase
from django.shortcuts import render, redirect
from django.contrib import auth
from django.http import HttpResponse, JsonResponse
from google.oauth2.credentials import Credentials
from googleapiclient.discovery import build
from google.auth.transport.requests import Request
import google.auth
from firebase_admin import credentials
import firebase_admin
import os
from .models import List, ListSerializer
from google.cloud import firestore


config = {
    "apiKey": "AIzaSyA3LauUxjPjzQb1nHrZbWmz4ZY3sTwccHA",
    "authDomain": "e-waste-5ecff.firebaseapp.com",
    "databaseURL": "https://e-waste-5ecff-default-rtdb.firebaseio.com",
    "projectId": "e-waste-5ecff",
    "storageBucket": "e-waste-5ecff.appspot.com",
    "messagingSenderId": "172868184902",
    "appId": "1:172868184902:web:efe1f3aa1a3451b4af9347",
}

firebase = pyrebase.initialize_app(config)
authe = firebase.auth()
database = firebase.database()


def index(request):
    return render(request, 'index.html')


def login(request):
    return render(request, 'login.html')

def quoteform(request):
    return render(request, 'quote.html')


def welcome(request):
    if request.method == 'GET':
        # Check if the user is already signed in
        user = authe.get_account_info(id_token=request.session['idToken'])
        return render(request, 'welcome.html', context={'e': user['users'][0]['email']})

    elif request.method == 'POST':
        # Authenticate the user using Firebase
        email = request.POST.get('email')
        password = request.POST.get('password')
        try:
            user = authe.sign_in_with_email_and_password(email, password)
        except:
            message = "Invalid credentials"
            return render(request, 'login.html', context={"message": message})

        # Store the Firebase ID token in the session
        session_id = user['idToken']
        request.session['idToken'] = str(session_id)

        return redirect('welcome')


def google_login(request):
    """# redirect user to Firebase authentication URL
    auth_url = "https://accounts.google.com/o/oauth2/auth?client_id=666221336175-oi195r8g97uhntq1ikvcohjc52hu0me4.apps.googleusercontent.com&redirect_uri=http://localhost:8000/e-waste-5ecff/firebase_auth&response_type=code&scope=email%20profile&access_type=offline"
    credentials = flow.fetch_token(code=code)
    request.session['google_auth_credentials'] = credentials.to_authorized_user_info()
    return redirect(auth_url)"""

def videos(request):
    return render(request, 'video.html')

def firebase_callback(request):
    # retrieve authorization code from request
    code = request.GET.get('code')

    # exchange authorization code for access token and user info
    credentials = Credentials.from_authorization_code(
        code, redirect_uri='http://localhost:8000/e-waste-5ecff/firebase_auth')
    id_token = credentials.id_token
    user = auth.verify_id_token(id_token)

    # check if user exists in Django database
    try:
        django_user = user.objects.get(username=user['email'])
    except user.DoesNotExist:
        # create new user if user does not exist
        django_user = user.objects.create_user(
            username=user['email'], email=user['email'], password=None)

    # store user's Firebase credentials in Django session
    request.session['firebase_user'] = user

    # redirect user to home page
    return redirect('home')


def logout(request):
    auth.logout(request)
    return render(request, 'login.html')


def signup(request):
    name = request.POST.get("signupname")
    email = request.POST.get("signupemail")
    passw = request.POST.get("signuppass")

    user = authe.create_user_with_email_and_password(email, passw)

    uid = user['uid']
    data = {
        "address": "abc",
        "age": 9,
        "imageurl": "https://firebasestorage.googleapis.com/v0/b/e-waste-5ecff.appspot.com/o/Profile%2F1678470773352?alt=media&token=711702e4-14d4-438c-8a33-a84d48b9fc64",
        "name": name,
    }
    database.child("Users").child(uid).set(data)
 
def home(request):
    authorization_code = request.GET.get('code')
    credentials = Credentials.from_authorized_user_info(info=request.session['google_auth_credentials'])
    google_client = build('oauth2', 'v2', credentials=credentials)
    user_info = google_client.userinfo().get().execute()
    email = user_info['email']
    profile_info = google_client.userinfo().get().execute()
    name = profile_info['name']
    picture = profile_info['picture']
    dict = {'email': email, 'name': name, 'picture': picture}
    return JsonResponse(dict, safe=False)

os.environ['GOOGLE_APPLICATION_CREDENTIALS'] = 'firebase-adminsdk.json.json'

db = firestore.Client(project='reclaimit-lists')

def quotewelcome(request):
    if request.method == "POST":
        first_name = request.POST.get("firstname")
        last_name = request.POST.get("lastname")
        name = " ".join([first_name, last_name])
        age = request.POST.get("age")
        dob = request.POST.get("dob")
        gender = request.POST.get("gender")
        email = request.POST.get("email")
        address_line_1 = request.POST.get("address1")
        address_line_2 = request.POST.get("address2")
        address = " ".join([address_line_1, address_line_2])
        phone = request.POST.get("phone")
        zip = request.POST.get("post")
        city = request.POST.get("city")
        list = request.POST.get("list")
        data = {
            "name" : name,
            "age" : age,
            "dob" : dob,
            "gender" : gender,
            "email" : email,
            "address" : address,
            "phone" : phone,
            "zip" : zip,
            "city" : city,
            "list" : list
        }
        seriliazer = ListSerializer(data=data)
        if seriliazer.is_valid():
            seriliazer.save()
            return render(request, "index.html", context={"message" : "Your request has been recorded! Check your mail for further details!"})
        print(seriliazer.error_messages)
        print(seriliazer.errors)
    return render(request, "quote.html")