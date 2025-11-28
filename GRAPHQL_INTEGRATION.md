# Intégration GraphQL - RangoApp

Ce document décrit l'intégration de l'API GraphQL dans l'application Android RangoApp.

## Configuration

### URL de l'API

L'URL de l'API GraphQL est configurée dans `Constants.kt`:
- **Émulateur Android**: `http://10.0.2.2:8080/query` (10.0.2.2 est l'alias de localhost pour l'émulateur)
- **Appareil physique**: Remplacez par l'IP de votre machine (ex: `http://192.168.1.100:8080/query`)

### Dépendances

Les dépendances Apollo GraphQL ont été ajoutées:
- `apollo-runtime`: Client GraphQL
- `apollo-api`: API Apollo
- `okhttp`: Client HTTP pour les requêtes

## Structure

### Fichiers GraphQL

Les fichiers `.graphql` sont situés dans `app/src/main/graphql/`:
- **Queries**: `MeQuery.graphql`, `ProductsQuery.graphql`, `CompanyQuery.graphql`, etc.
- **Mutations**: `LoginMutation.graphql`, `RegisterMutation.graphql`, `CreateProductMutation.graphql`, etc.

### Client GraphQL

Le client GraphQL est configuré dans `GraphQLClient.kt`:
- Gère automatiquement l'ajout du token JWT dans les headers
- Utilise OkHttp pour les requêtes HTTP
- Injecté via Hilt/Dagger

### Gestion du Token

Le `TokenManager` gère le stockage et la récupération du token JWT:
- Stockage dans SharedPreferences
- Sauvegarde automatique après login/register
- Suppression lors du logout

## Utilisation

### Authentification

```kotlin
@Inject
lateinit var graphQLAuthDataSource: GraphQLAuthDataSource

// Login
graphQLAuthDataSource.login(phone, password).collect { response ->
    when (response) {
        is BaseResponse.Success -> {
            val session = response.data
            // Token sauvegardé automatiquement
        }
        is BaseResponse.Error -> {
            // Gérer l'erreur
        }
        is BaseResponse.Loading -> {
            // Afficher le loading
        }
    }
}

// Register
graphQLAuthDataSource.register(
    email, password, name, phone,
    companyName, companyAddress, companyPhone,
    companyDescription, companyType,
    storeName, storeAddress, storePhone
).collect { response ->
    // Gérer la réponse
}
```

### Exemple: Récupérer les produits

```kotlin
@Inject
lateinit var apolloClient: ApolloClient

suspend fun getProducts(storeId: String?) {
    val response = apolloClient.query(
        ProductsQuery(storeId)
    ).execute()
    
    if (!response.hasErrors()) {
        val products = response.data?.products
        // Utiliser les produits
    }
}
```

## Migration depuis Appwrite

Pour migrer complètement vers GraphQL:

1. **Créer les data sources GraphQL** pour chaque module (Product, Client, Provider, etc.)
2. **Mettre à jour les repositories** pour utiliser les nouvelles data sources
3. **Mettre à jour le module DI** pour injecter les nouvelles dépendances
4. **Tester** chaque fonctionnalité

## Génération des Types

Apollo génère automatiquement les types Kotlin à partir des fichiers `.graphql` lors de la compilation:
- Types générés dans `com.avenir.rangoapp.graphql`
- Utilisez ces types dans vos data sources

## Notes Importantes

1. **Token JWT**: Le token est automatiquement ajouté aux requêtes via l'interceptor OkHttp
2. **Erreurs**: Vérifiez toujours `response.hasErrors()` avant d'utiliser les données
3. **Threading**: Apollo gère automatiquement le threading, mais utilisez `suspend` pour les coroutines
4. **Cache**: Apollo peut mettre en cache les requêtes (configurable)

## Prochaines Étapes

1. Créer les data sources GraphQL pour:
   - Products
   - Clients
   - Providers
   - Factures
   - Stores
   - RapportStore
   - Users

2. Mettre à jour les ViewModels pour utiliser les nouvelles data sources

3. Tester l'intégration complète







