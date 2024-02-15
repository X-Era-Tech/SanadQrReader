package com.xera.sanadqrreader.data.remote.utils

sealed class NetworkException(message: String?) : Exception(message) {
    class NotFoundException : NetworkException("Resource not found")
    class ApiKeyExpiredException : NetworkException("API key expired")
    class UnAuthorizedException : NetworkException("Unauthorized")
    class NoInternetException : NetworkException("No internet connection")
    class InternalServerError : NetworkException("Internal server error")
}


