package com.blumock.domain.usecases

interface UseCase<A, R> {

    suspend operator fun invoke(arg: A): R
}