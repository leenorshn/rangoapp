package com.avenir.rangoapp.di

import android.content.Context
import com.avenir.rangoapp.data.datasource.AuthDataSource
import com.avenir.rangoapp.data.datasource.CompanyDataSource
import com.avenir.rangoapp.data.datasource.ProductDataSource
import com.avenir.rangoapp.data.datasource.RapportStoreDataSource
import com.avenir.rangoapp.data.domaine.AuthRepositoryImpl
import com.avenir.rangoapp.data.domaine.CompanyRepositoryImpl
import com.avenir.rangoapp.data.domaine.ProductRepositoryImpl
import com.avenir.rangoapp.data.domaine.RapportStoreRepositoryImpl
import com.avenir.rangoapp.data.repository.AuthRepository
import com.avenir.rangoapp.data.repository.CompanyRepository
import com.avenir.rangoapp.data.repository.ProductRepository
import com.avenir.rangoapp.data.repository.RapportStoreRepository
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
    fun provideProductRepository(dataSource: ProductDataSource):ProductRepository{
        return ProductRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideRapportStoreDataSource(database: Databases,account: Account):RapportStoreDataSource{
        return RapportStoreDataSource(database = database, account = account)
    }

    @Provides
    @Singleton
    fun provideRapportStoreRepository(dataSource: RapportStoreDataSource):RapportStoreRepository{
        return RapportStoreRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideProductDataSource(database: Databases,account: Account):ProductDataSource{
        return ProductDataSource(database = database, account = account)
    }

    @Provides
    @Singleton
    fun provideCompanyDataSource(database: Databases,account: Account): CompanyDataSource {
       return  CompanyDataSource(database,account)
    }

    @Provides
    @Singleton
    fun provideCompanyRepository(companyDataSource: CompanyDataSource): CompanyRepository {
        return CompanyRepositoryImpl(companyDataSource)
    }

}