from . import views
from django.urls import path

urlpatterns = [
    path('', views.index, name="index"),
    path('login', views.login, name="login"),
    path('google-login', views.google_login, name='google-login'),
    path('firebase-callback', views.firebase_callback, name='firebase-callback'),
    path('welcome/', views.welcome, name="welcome"),
    path('logout', views.logout, name="logout"),
    path('signup', views.signup, name="signup"),
    path('e-waste-5ecff/firebase_auth', views.home, name="home"),
    path('quoteform', views.quoteform, name="quoteform"),
    path("welcome_quote", views.quotewelcome, name="welcome_quote"),
    path('videos', views.videos, name="videos")
]