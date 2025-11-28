package com.avenir.rangoapp.di

import android.content.Context
import com.avenir.rangoapp.data.datasource.GraphQLAuthDataSource
import com.avenir.rangoapp.data.datasource.GraphQLStoreDataSource
import com.avenir.rangoapp.data.datasource.GraphQLProductDataSource
import com.avenir.rangoapp.data.datasource.GraphQLCompanyDataSource
import com.avenir.rangoapp.data.datasource.GraphQLRapportStoreDataSource
import com.avenir.rangoapp.data.datasource.GraphQLUserDataSource
import com.avenir.rangoapp.data.datasource.CompanyDataStore
import com.avenir.rangoapp.data.datasource.SharePrefDB
import com.avenir.rangoapp.data.domaine.AuthRepositoryImpl
import com.avenir.rangoapp.data.domaine.StoreRepositoryImpl
import com.avenir.rangoapp.data.domaine.CompanyRepositoryImpl
import com.avenir.rangoapp.data.domaine.RapportStoreRepositoryImpl
import com.avenir.rangoapp.data.domaine.UserRepositoryImpl
import com.avenir.rangoapp.data.repository.AuthRepository
import com.avenir.rangoapp.data.repository.StoreRepository
import com.avenir.rangoapp.data.repository.CompanyRepository
import com.avenir.rangoapp.data.repository.RapportStoreRepository
import com.avenir.rangoapp.data.repository.UserRepository
import com.avenir.rangoapp.core.TokenManager
import com.avenir.rangoapp.core.GraphQLClient
import com.apollographql.apollo3.ApolloClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        return CompanyDataStore(context = context)
    }

    @Provides
    @Singleton
    fun provideSharePref(@ApplicationContext context: Context): SharePrefDB {
        return SharePrefDB(context = context)
    }

    // ========== GraphQL Configuration ==========
    
    @Provides
    @Singleton
    fun provideTokenManager(@ApplicationContext context: Context): TokenManager {
        return TokenManager(context)
    }

    @Provides
    @Singleton
    fun provideGraphQLClient(tokenManager: TokenManager): GraphQLClient {
        return GraphQLClient(tokenManager)
    }

    @Provides
    @Singleton
    fun provideApolloClient(graphQLClient: GraphQLClient): ApolloClient {
        return graphQLClient.getClient()
    }

    @Provides
    @Singleton
    fun provideGraphQLAuthDataSource(
        apolloClient: ApolloClient,
        tokenManager: TokenManager,
        companyDataStore: CompanyDataStore
    ): GraphQLAuthDataSource {
        return GraphQLAuthDataSource(apolloClient, tokenManager, companyDataStore)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(graphQLAuthDataSource: GraphQLAuthDataSource): AuthRepository {
        return AuthRepositoryImpl(graphQLAuthDataSource)
    }

    // ========== Store Repository ==========
    
    @Provides
    @Singleton
    fun provideGraphQLStoreDataSource(apolloClient: ApolloClient): GraphQLStoreDataSource {
        return GraphQLStoreDataSource(apolloClient)
    }

    @Provides
    @Singleton
    fun provideStoreRepository(graphQLStoreDataSource: GraphQLStoreDataSource): StoreRepository {
        return StoreRepositoryImpl(graphQLStoreDataSource)
    }

    // ========== Product Repository ==========
    
    @Provides
    @Singleton
    fun provideGraphQLProductDataSource(
        apolloClient: ApolloClient,
        companyDataStore: CompanyDataStore
    ): GraphQLProductDataSource {
        return GraphQLProductDataSource(apolloClient, companyDataStore)
    }

    @Provides
    @Singleton
    fun provideProductRepository(
        graphQLProductDataSource: GraphQLProductDataSource
    ): com.avenir.rangoapp.data.repository.ProductRepository {
        return com.avenir.rangoapp.data.domaine.ProductRepositoryImpl(graphQLProductDataSource)
    }

    // ========== Company Repository ==========
    
    @Provides
    @Singleton
    fun provideGraphQLCompanyDataSource(apolloClient: ApolloClient): GraphQLCompanyDataSource {
        return GraphQLCompanyDataSource(apolloClient)
    }

    @Provides
    @Singleton
    fun provideCompanyRepository(
        graphQLCompanyDataSource: GraphQLCompanyDataSource
    ): CompanyRepository {
        return CompanyRepositoryImpl(graphQLCompanyDataSource)
    }

    // ========== RapportStore Repository ==========
    
    @Provides
    @Singleton
    fun provideGraphQLRapportStoreDataSource(
        apolloClient: ApolloClient,
        companyDataStore: CompanyDataStore
    ): GraphQLRapportStoreDataSource {
        return GraphQLRapportStoreDataSource(apolloClient, companyDataStore)
    }

    @Provides
    @Singleton
    fun provideRapportStoreRepository(
        graphQLRapportStoreDataSource: GraphQLRapportStoreDataSource
    ): RapportStoreRepository {
        return RapportStoreRepositoryImpl(graphQLRapportStoreDataSource)
    }

    // ========== User Repository ==========
    
    @Provides
    @Singleton
    fun provideGraphQLUserDataSource(apolloClient: ApolloClient): GraphQLUserDataSource {
        return GraphQLUserDataSource(apolloClient)
    }

    @Provides
    @Singleton
    fun provideUserRepository(
        graphQLUserDataSource: GraphQLUserDataSource
    ): UserRepository {
        return UserRepositoryImpl(graphQLUserDataSource)
    }

    // ========== Note: Other repositories (Client, Provider, etc.) ==========
    // ========== will be added here as GraphQL data sources are created ==========
}
