package com.ufam.tcc.security.controller.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class Dispatcher(val dispatcher: UFAMDispatchers)

enum class UFAMDispatchers {
    Default,
    IO,
}