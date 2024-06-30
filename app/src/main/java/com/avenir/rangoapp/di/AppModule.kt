package com.avenir.rangoapp.di

import android.content.Context
import com.avenir.rangoapp.data.datasource.AuthDataSource
import com.avenir.rangoapp.data.datasource.CompanyDataSource
import com.avenir.rangoapp.data.domaine.AuthRepositoryImpl
import com.avenir.rangoapp.data.domaine.CompanyRepositoryImpl
import com.avenir.rangoapp.data.repository.AuthRepository
import com.avenir.rangoapp.data.repository.CompanyRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.appwrite.Client
import io.appwrite.services.Account
import io.appwrite.services.Databases
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }

    @Provides
    @Singleton
    fun provideAppWriteClient(@ApplicationContext context: Context):Client {
      val  client = Client(context)
            .setEndpoint("https://cloud.appwrite.io/v1")
            .setProject("66793f0a00167968cfad")

       return client
    }

    @Provides
    @Singleton
    fun provideAccount(client: Client) :Account {
      val  account= Account(client)

        return  account
    }

    @Provides
    @Singleton
    fun provideDatabase(client: Client):Databases{
        val database=Databases(client)
        return database
    }

    @Provides
    @Singleton
    fun provideUserDataSource(account: Account,database: Databases): AuthDataSource {
        return AuthDataSource(account,database)
    }

    @Provides
    @Singleton
    fun provideUserRepository(authDataSource: AuthDataSource): AuthRepository {
        return AuthRepositoryImpl(authDataSource)
    }

    @Provides
    @Singleton
    fun provideCompanyDataSource(database: Databases): CompanyDataSource {
       return  CompanyDataSource(database)
    }

    @Provides
    @Singleton
    fun provideCompanyRepository(companyDataSource: CompanyDataSource): CompanyRepository {
        return CompanyRepositoryImpl(companyDataSource)
    }

}