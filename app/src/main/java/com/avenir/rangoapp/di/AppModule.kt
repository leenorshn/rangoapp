package com.avenir.rangoapp.di

import android.content.Context
import com.avenir.rangoapp.data.datasource.AuthDataSource
import com.avenir.rangoapp.data.datasource.CompanyDataSource
import com.avenir.rangoapp.data.datasource.CompanyDataStore
import com.avenir.rangoapp.data.datasource.ProductDataSource
import com.avenir.rangoapp.data.datasource.ProviderDataSource
import com.avenir.rangoapp.data.datasource.RapportStoreDataSource
import com.avenir.rangoapp.data.datasource.VenteDataSource
import com.avenir.rangoapp.data.datasource.SharePrefDB
import com.avenir.rangoapp.data.datasource.UserDataSource
import com.avenir.rangoapp.data.domaine.AuthRepositoryImpl
import com.avenir.rangoapp.data.domaine.CompanyRepositoryImpl
import com.avenir.rangoapp.data.domaine.ProductRepositoryImpl
import com.avenir.rangoapp.data.domaine.ProviderRepositoryImpl
import com.avenir.rangoapp.data.domaine.RapportStoreRepositoryImpl
import com.avenir.rangoapp.data.domaine.UserRepositoryImpl
import com.avenir.rangoapp.data.domaine.VenteRepositoryImpl
import com.avenir.rangoapp.data.repository.AuthRepository
import com.avenir.rangoapp.data.repository.CompanyRepository
import com.avenir.rangoapp.data.repository.ProductRepository
import com.avenir.rangoapp.data.repository.ProviderRepository
import com.avenir.rangoapp.data.repository.RapportStoreRepository
import com.avenir.rangoapp.data.repository.UserRepository
import com.avenir.rangoapp.data.repository.VenteRepository
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
    fun provideDataStore(@ApplicationContext context: Context): CompanyDataStore {
        return CompanyDataStore(context=context)
    }

    @Provides
    @Singleton
    fun provideSharePref(@ApplicationContext context: Context): SharePrefDB {
        return SharePrefDB(context=context)
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
    fun provideUserDataSource(account: Account,database: Databases,companyDataStore: CompanyDataStore): AuthDataSource {
        return AuthDataSource(account,database,companyDataStore=companyDataStore )
    }

    @Provides
    @Singleton
    fun provideUserRepository(authDataSource: AuthDataSource): AuthRepository {
        return AuthRepositoryImpl(authDataSource)
    }

    @Provides
    @Singleton
    fun provideVenteRepository(dataSource: VenteDataSource):VenteRepository{
        return VenteRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideVenteDataSource(database: Databases,companyDataStore: CompanyDataStore):VenteDataSource{
        return VenteDataSource(database = database, companyDataStore = companyDataStore)
    }

    @Provides
    @Singleton
    fun provideProviderRepository(dataSource: ProviderDataSource):ProviderRepository{
        return ProviderRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideProviderDataSource(database: Databases,companyDataStore: CompanyDataStore):ProviderDataSource{
        return ProviderDataSource(database = database, companyDataStore = companyDataStore )
    }

    @Provides
    @Singleton
    fun provideProductRepository(dataSource: ProductDataSource):ProductRepository{
        return ProductRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideRapportStoreDataSource(database: Databases,companyDataStore: CompanyDataStore):RapportStoreDataSource{
        return RapportStoreDataSource(database = database, companyDataStore = companyDataStore)
    }

    @Provides
    @Singleton
    fun provideRapportStoreRepository(dataSource: RapportStoreDataSource):RapportStoreRepository{
        return RapportStoreRepositoryImpl(dataSource)
    }

    @Provides
    @Singleton
    fun provideProductDataSource(database: Databases,companyDataStore: CompanyDataStore):ProductDataSource{
        return ProductDataSource(database = database, companyDataStore = companyDataStore)
    }

    @Provides
    @Singleton
    fun provideCompanyDataSource(database: Databases,account: Account,companyDataStore: CompanyDataStore): CompanyDataSource {
       return  CompanyDataSource(database,account, companyDataStore = companyDataStore)
    }

    @Provides
    @Singleton
    fun provideCompanyRepository(companyDataSource: CompanyDataSource): CompanyRepository {
        return CompanyRepositoryImpl(companyDataSource)
    }

//    @Provides
//    @Singleton
//    fun provideUserDataSource(database: Databases,account: Account,companyDataStore: CompanyDataStore): UserDataSource {
//        return  UserDataSource(database,account, companyDataStore = companyDataStore)
//    }
//
//    @Provides
//    @Singleton
//    fun provideUserRepository(datasource: UserDataSource): UserRepository {
//        return UserRepositoryImpl(datasource)
//    }

}