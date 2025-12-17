# Prompt pour Application Android RangoApp - Jetpack Compose

## 📱 Vue d'ensemble

Application Android native **RangoApp** pour la gestion de boutiques/magasins avec support multi-boutiques, multi-utilisateurs et système de permissions basé sur les rôles. L'application utilise **Jetpack Compose** pour l'UI et consomme une API GraphQL existante.

## 🎯 État Actuel

Application Android en développement avancé avec les fonctionnalités suivantes déjà implémentées:
- ✅ Architecture MVVM avec Clean Architecture
- ✅ Module Ventes (Sales) avec CRUD complet
- ✅ Module Stock/Produits avec notifications de succès
- ✅ Module Clients avec notifications de succès
- ✅ Module Utilisateurs avec CRUD + Block/Unblock
- ✅ Système de permissions basé sur les rôles
- ✅ Settings menu (flat list)
- ✅ Company et Store creation
- ✅ GraphQL integration avec Apollo Client
- ✅ Date format RFC3339 (ISO 8601)

---

## 🏗️ Architecture Technique

### Stack Technologique
- **UI**: Jetpack Compose
- **Language**: Kotlin
- **Architecture**: MVVM (Model-View-ViewModel) avec Clean Architecture
- **Gestion d'état**: StateFlow / MutableStateFlow
- **Injection de dépendances**: Hilt
- **Navigation**: Compose Navigation
- **API**: GraphQL (Apollo Client pour Android)
- **Stockage local**: 
  - Room (pour cache offline)
  - DataStore (pour les tokens et préférences)
- **Async**: Coroutines + Flow
- **Génération PDF**: iText ou Android PdfDocument
- **Minimum SDK**: 24 (Android 7.0)
- **Target SDK**: 34 (Android 14)

### API GraphQL
- **URL de base**: `https://rango-393061966089.europe-west1.run.app/query`
- **Authentification**: JWT (accessToken + refreshToken)
- **Headers requis**: 
  - `Authorization: Bearer {accessToken}`
  - `Content-Type: application/json`
- **Date Format**: RFC3339 (ISO 8601) - `yyyy-MM-dd'T'HH:mm:ss'Z'`

---

## 👤 Système d'Authentification

### Écrans requis
1. **Écran de Bienvenue** (`WelcomeScreen`)
   - Logo de l'application
   - Nom "Xshop"
   - Boutons: "Se connecter" et "S'inscrire"

2. **Écran de Connexion** (`LoginScreen`)
   - Champs:
     - Numéro de téléphone (TextInput)
     - Mot de passe (TextInput password)
   - Bouton "Se connecter"
   - Lien vers "S'inscrire"
   - Validation des champs obligatoires

3. **Écran d'Inscription** (`RegisterScreen`)
   - Champs:
     - Nom complet
     - Numéro de téléphone
     - Mot de passe
     - Confirmation du mot de passe
   - Bouton "S'inscrire"
   - Lien vers "Se connecter"

### GraphQL Mutations

#### Login
```graphql
mutation Login($phone: String!, $password: String!) {
  login(phone: $phone, password: $password) {
    user {
      id
      name
      phone
      role
      isBlocked
      companyId
      storeIds
      assignedStoreId
    }
    accessToken
    refreshToken
  }
}
```

#### Register
```graphql
mutation Register($input: RegisterInput!) {
  register(input: $input) {
    user {
      id
      name
      phone
      role
      isBlocked
      companyId
      storeIds
      assignedStoreId
    }
    accessToken
    refreshToken
  }
}
```

**Input RegisterInput:**
```kotlin
data class RegisterInput(
    val name: String,
    val phone: String,
    val password: String,
    val companyName: String,
    val companyAddress: String,
    val companyPhone: String,
    val storeName: String,
    val storeAddress: String,
    val storePhone: String
)
```

#### Logout
```graphql
mutation Logout {
  logout
}
```

### Gestion des Tokens
- **Stocker** `accessToken` et `refreshToken` dans **DataStore** (chiffré)
- **Ajouter** automatiquement `Authorization: Bearer {token}` à chaque requête GraphQL
- **NE PAS** envoyer de token pour les mutations: `Login`, `Register`, `Logout`
- **Rafraîchir** automatiquement le token si erreur 401
- **Rediriger** vers Login si token invalide ou expiré

---

## 🏪 Gestion Multi-Boutiques

### Sélection de Boutique Active
- **État global**: Boutique active (activeStore)
- **Écran de sélection**: Si l'utilisateur a accès à plusieurs boutiques
- **Affichage**: Nom de la boutique active dans la TopAppBar
- **Changement**: Dropdown ou écran dédié pour changer de boutique

### Types GraphQL
```kotlin
data class Store(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val companyId: String,
    val company: CompanyInfo?,
    val createdAt: String,
    val updatedAt: String
)

data class CompanyInfo(
    val id: String,
    val name: String
)

data class Company(
    val id: String,
    val name: String,
    val address: String,
    val phone: String,
    val email: String?,
    val description: String,
    val type: String,
    val logo: String?,
    val rccm: String?, // Registre de commerce
    val idNat: String?,
    val idCommerce: String?,
    val stores: List<StoreInfo>?,
    val createdAt: String,
    val updatedAt: String
)

data class StoreInfo(
    val id: String,
    val name: String,
    val address: String?,
    val phone: String?
)
```

---

## 🎭 Système de Permissions (Rôles)

### Rôles disponibles
1. **ADMIN** (Administrateur)
   - Accès complet à toutes les fonctionnalités
   - Peut gérer les utilisateurs, boutiques, paramètres

2. **USER** (Utilisateur simple)
   - Accès limité:
     - ✅ Dashboard (lecture seule)
     - ✅ Ventes (créer, modifier, imprimer factures)
     - ✅ Stock (lecture seule - voir les produits uniquement)
     - ❌ Pas de création/modification de produits
     - ❌ Pas d'accès aux clients, caisse, paramètres

### Implémentation des Permissions
```kotlin
enum class Permission {
    // Navigation
    VIEW_DASHBOARD,
    VIEW_VENTES,
    VIEW_STOCK,
    VIEW_CLIENTS,
    VIEW_CAISSE,
    VIEW_SETTINGS,
    
    // Ventes
    CREATE_VENTE,
    EDIT_VENTE,
    DELETE_VENTE,
    GENERATE_FACTURE,
    
    // Produits
    VIEW_PRODUCTS,
    CREATE_PRODUCT,
    EDIT_PRODUCT,
    DELETE_PRODUCT,
    
    // Clients
    VIEW_CLIENT,
    CREATE_CLIENT,
    EDIT_CLIENT,
    DELETE_CLIENT,
    
    // Caisse
    VIEW_CAISSE,
    CREATE_CAISSE_ENTRY,
    
    // Paramètres
    MANAGE_USERS,
    MANAGE_STORES,
    MANAGE_COMPANY,
    MANAGE_SUBSCRIPTION
}

object PermissionsManager {
    private val rolePermissions = mapOf(
        "ADMIN" to Permission.values().toList(), // Toutes les permissions
        "USER" to listOf(
            Permission.VIEW_DASHBOARD,
            Permission.VIEW_VENTES,
            Permission.CREATE_VENTE,
            Permission.EDIT_VENTE,
            Permission.GENERATE_FACTURE,
            Permission.VIEW_STOCK,
            Permission.VIEW_PRODUCTS
        )
    )
    
    fun hasPermission(role: String, permission: Permission): Boolean {
        return rolePermissions[role]?.contains(permission) == true
    }
    
    fun isAdmin(role: String): Boolean = role == "ADMIN"
}
```

### UI Conditionnelle
- **Masquer** les boutons/écrans selon les permissions
- **Filtrer** la navigation selon le rôle
- **Exemple**:
```kotlin
if (PermissionsManager.hasPermission(userRole, Permission.CREATE_PRODUCT)) {
    Button(onClick = { /* Créer produit */ }) {
        Text("Nouveau produit")
    }
}
```

---

## 📱 Structure de Navigation

### Bottom Navigation (Utilisateur ADMIN)
1. 🏠 **Dashboard** (`/dashboard`)
2. 💰 **Ventes** (`/ventes`)
3. 📦 **Stock** (`/stock`)
4. 👥 **Clients** (`/clients`)
5. 💵 **Caisse** (`/caisse`)

### Bottom Navigation (Utilisateur USER)
1. 🏠 **Dashboard** (`/dashboard`)
2. 💰 **Ventes** (`/ventes`)
3. 📦 **Stock** (`/stock`) - lecture seule

### Top App Bar
- **Titre**: Nom de l'écran actuel
- **Actions**:
  - Icône de notification (optionnel)
  - Icône de changement de boutique
  - Icône de paramètres (ADMIN uniquement)
  - Icône de profil / déconnexion

### Menu Latéral (Navigation Drawer) - Optionnel
- Profil utilisateur
- Nom de la boutique active
- Liste des boutiques (si plusieurs)
- Paramètres
- Aide
- À propos
- Déconnexion

---

## 🏠 Module Dashboard

### Écran Principal (`DashboardScreen`)

#### Statistiques à afficher (selon la boutique active)
1. **Ventes du jour**
   - Total des ventes (montant)
   - Nombre de ventes
   - Évolution par rapport à hier (%)

2. **Caisse**
   - Solde actuel
   - Entrées du jour
   - Sorties du jour
   - Devise sélectionnée (USD ou CDF)

3. **Stock**
   - Nombre total de produits
   - Produits en rupture de stock (stock < 5)
   - Valeur totale du stock

4. **Clients**
   - Nombre total de clients
   - Nouveaux clients du mois

5. **Sélecteur de période**
   - Aujourd'hui
   - Semaine
   - Mois
   - Année
   - Personnalisé (date picker)

6. **Sélecteur de devise**
   - USD
   - CDF

7. **Graphiques** (optionnel mais recommandé)
   - Graphique des ventes (ligne)
   - Graphique des bénéfices (barres)
   - Top 5 produits vendus (barres horizontales)

### GraphQL Queries

#### Dashboard Stats
```graphql
query DashboardStats($storeId: String!, $period: String, $currency: String) {
  salesStats(storeId: $storeId, period: $period, currency: $currency) {
    totalSales
    totalRevenue
    totalItems
    averageSale
    totalBenefice
  }
  caisse(storeId: $storeId, currency: $currency) {
    currentBalance
    in
    out
    currency
  }
  productsCount(storeId: $storeId)
  lowStockProducts(storeId: $storeId, threshold: 5)
  clientsCount(storeId: $storeId)
}
```

---

## 💰 Module Ventes (Sales)

### ✅ État d'implémentation
- ✅ Liste des ventes (SalesQuery)
- ✅ Détails d'une vente (SaleQuery)
- ✅ Création de vente (CreateSaleMutation)
- ✅ Suppression de vente (DeleteSaleMutation)
- ✅ Génération de facture depuis vente (CreateFactureFromSaleMutation)
- ✅ Modèles de données (SaleModel, SaleProductModel)
- ✅ Data Source (GraphQLSaleDataSource)
- ✅ Repository (VenteRepository, VenteRepositoryImpl)
- ✅ UI Components (SaleCardItem)

### Écrans requis

#### 1. Liste des Ventes (`FacturationScreen` - à renommer `SalesScreen`)
- **Affichage**: Liste des ventes (actuellement implémentée)
- **Filtres**:
  - Période (jour, semaine, mois, année, personnalisé)
  - Devise (USD, EUR, XAF, XOF, CDF, Toutes)
  - Statut de paiement (cash, dette, avance)
- **Recherche**: Par numéro, client
- **Actions par item**:
  - Voir détails (clic sur l'item)
  - Générer facture (icône imprimante)
- **Bouton FAB**: "Nouvelle Vente" (+)

**Item de liste (SaleCardItem):**
```kotlin
// Composant déjà implémenté: SaleCardItem.kt
// Affiche: date, priceToPay, pricePayed, change, currency, client
```

#### 2. Nouvelle Vente (`NewFactureScreen` - à renommer `NewSaleScreen`)
- **✅ État**: Partiellement implémenté
- **Champs**:
  - Sélection client (dropdown ou recherche) - **Optionnel**
    - Bouton "Nouveau client" (quick add)
  - Panier de produits (liste)
    - Bouton "Ajouter produit" → recherche + sélection
    - Pour chaque produit:
      - Nom du produit
      - Prix unitaire
      - Quantité (stepper: +/-)
      - Prix total (auto-calculé)
      - Bouton supprimer (X)
  - **Calculs automatiques**:
    - Prix total à payer (priceToPay)
    - Montant payé (pricePayed - input)
    - Monnaie à rendre (change - auto-calculé)
  - Devise (USD, EUR, XAF, XOF, CDF) - sélecteur
  - Date de vente (date picker RFC3339) - par défaut aujourd'hui
- **Format du panier pour GraphQL**:
  ```kotlin
  List<Triple<String, Double, Double>> // (productId, quantity, price)
  ```
- **Validation**:
  - Au moins 1 produit dans le panier
  - Quantités disponibles en stock
  - Montant payé ≥ 0
- **Bouton**: "Créer la vente"
- **Après création réussie**:
  - Retour à la liste des ventes
  - Message de succès (Snackbar)

#### 3. Détails Vente (`SaleDetailScreen`)
- **✅ État**: GraphQL query disponible (SaleQuery)
- **Affichage**: Toutes les informations de la vente
  - ID de vente
  - Date (RFC3339)
  - Client (nom, téléphone) - optionnel
  - Opérateur (qui a créé la vente)
  - Liste des produits vendus (basket):
    - productId, quantity, price
    - product { id, name, priceVente }
  - Prix total à payer (priceToPay)
  - Montant payé (pricePayed)
  - Monnaie rendue (change)
  - Devise (currency)
  - Store (id, name)
- **Actions**:
  - Bouton "Générer facture" (CreateFactureFromSale)
  - Bouton "Supprimer" (DeleteSale) - si permission

#### 4. Impression Facture
**✅ GraphQL Mutation disponible**: `CreateFactureFromSale(saleId: ID!)`
**❌ UI PDF non implémentée**

**TODO - Dialog de facture:**
- Afficher la facture formatée avec:
  - En-tête:
    - Nom de la boutique (store.name)
    - Adresse de la boutique (store.address)
    - Téléphone de la boutique (store.phone)
  - Informations facture:
    - Numéro de facture (factureNumber)
    - Date et heure
    - Client (nom, téléphone) - si disponible
  - Tableau des produits:
    - Colonnes: Nom | Qté | Prix Unit. | Total
  - Totaux:
    - Total (price)
    - Devise (currency)
  - Pied de page:
    - "Merci de votre confiance"
    - "RangoApp - Gestion de boutique"
- **Bouton "Imprimer"**:
  - Génère un **PDF** de la facture
  - Partage le PDF (Intent share ou enregistrement dans Downloads)

### GraphQL Mutations & Queries

#### Créer une vente
```graphql
mutation CreateSale($input: CreateSaleInput!) {
  createSale(input: $input) {
    id
    basket {
      productId
      product {
        id
        name
        priceVente
      }
      quantity
      price
    }
    priceToPay
    pricePayed
    change
    currency
    paymentType
    amountDue
    debtStatus
    debtId
    client {
      id
      name
      phone
    }
    operator {
      id
      name
      phone
    }
    storeId
    store {
      id
      name
    }
    date
    createdAt
    updatedAt
  }
}
```

**Input:**
```kotlin
data class CreateSaleInput(
    val basket: List<SaleBasketItem>,
    val priceToPay: Double,
    val pricePayed: Double,
    val clientId: String?, // Optionnel
    val storeId: String,
    val currency: String, // "USD" ou "CDF"
    val paymentType: String?, // "cash", "debt", "advance"
    val date: String? // Format ISO 8601
)

data class SaleBasketItem(
    val productId: String,
    val quantity: Int,
    val price: Double
)
```

#### ✅ Créer facture depuis une vente (CreateFactureFromSaleMutation.graphql)
```graphql
mutation CreateFactureFromSale($saleId: ID!) {
  createFactureFromSale(saleId: $saleId) {
    id
    factureNumber
    date
    price
    currency
    client { id name phone }
    store { id name address phone }
    products {
      productId
      quantity
      price
      product { id name }
    }
  }
}
```

#### ✅ Liste des ventes (SalesQuery.graphql)
```graphql
query SalesByStore($storeId: String) {
  sales(storeId: $storeId) {
    id
    date
    priceToPay
    pricePayed
    change
    currency
    client { id name }
    operator { id name }
  }
}
```

**NOTE**: Pagination non implémentée actuellement. À ajouter si nécessaire.

#### ✅ Détails d'une vente (SaleQuery.graphql)
```graphql
query SaleDetail($id: ID!) {
  sale(id: $id) {
    id
    date
    priceToPay
    pricePayed
    change
    currency
    client { id name phone }
    operator { id name }
    store { id name }
    basket {
      productId
      quantity
      price
      product {
        id
        name
        mark
        priceVente
      }
    }
  }
}
```

### ✅ Types Kotlin (Modèles existants)
```kotlin
// Fichier: SaleModel.kt
data class SaleModel(
    val id: String,
    val date: String, // RFC3339 format
    val priceToPay: Number,
    val pricePayed: Number,
    val change: Number,
    val currency: String,
    val client: ClientInfo?,
    val operator: OperatorInfo?,
    val store: StoreInfo?,
    val basket: List<SaleProductModel>?
)

data class SaleProductModel(
    val productId: String,
    val quantity: Number,
    val price: Number,
    val product: ProductInfo?
)

data class ClientInfo(
    val id: String,
    val name: String,
    val phone: String? = null
)

data class OperatorInfo(
    val id: String,
    val name: String
)

data class ProductInfo(
    val id: String,
    val name: String,
    val mark: String? = null,
    val priceVente: Number? = null
)

// Type Facture (pour CreateFactureFromSale)
data class Facture(
    val id: String,
    val factureNumber: String,
    val date: String,
    val price: Number,
    val currency: String,
    val client: ClientInfo?,
    val store: StoreInfo?,
    val products: List<SaleProductModel>
)
```

---

## 📦 Module Stock (Produits)

### Écrans requis

#### 1. Liste des Produits (`ProductsListScreen`)
- **✅ État**: Implémenté avec notifications
- **Affichage**: Liste des produits
- **Recherche**: Par nom, marque
- **Notifications**:
  - ✅ Snackbar "Produit ajouté avec succès" après création
  - ✅ Rafraîchissement automatique de la liste (OnRefreshProducts)
- **Item de produit**:
  - Nom du produit
  - Marque
  - Stock disponible
  - Prix de vente
  - Devise
- **Bouton FAB**: "Nouveau produit" (+) - **Seulement si permission CREATE_PRODUCT**
- **Clic sur item**: Voir détails

#### 2. Détails Produit (`ProductDetailScreen`)
- **Affichage**:
  - Image du produit (si disponible)
  - Nom
  - Marque
  - Prix d'achat
  - Prix de vente
  - Marge bénéficiaire (calculée)
  - Stock disponible
  - Devise
  - Fournisseur (nom, téléphone)
  - Date de création
  - Date de dernière modification
- **Actions**:
  - Bouton "Modifier" - **Seulement si permission EDIT_PRODUCT**
  - Bouton "Supprimer" - **Seulement si permission DELETE_PRODUCT**

#### 3. Nouveau Produit (`NewProductScreen`) - ADMIN uniquement
- **Champs**:
  - Nom * (requis)
  - Marque (optionnel)
  - Prix d'achat * (number)
  - Prix de vente * (number)
  - Stock initial * (number)
  - Devise * (USD ou CDF)
  - Fournisseur (dropdown - optionnel)
  - Image (upload depuis galerie ou caméra)
- **Validation**:
  - Prix de vente > Prix d'achat (recommandé, pas bloquant)
  - Stock ≥ 0
- **Bouton**: "Créer le produit"

#### 4. Modifier Produit (`EditProductScreen`) - ADMIN uniquement
- Même formulaire que "Nouveau Produit"
- Pré-rempli avec les données existantes
- **Bouton**: "Enregistrer les modifications"

### GraphQL Mutations & Queries

#### Liste des produits
```graphql
query Products($storeId: String!) {
  products(storeId: $storeId) {
    id
    name
    mark
    priceVente
    priceAchat
    stock
    currency
    providerId
    provider {
      id
      name
      phone
    }
    storeId
    image
    createdAt
    updatedAt
  }
}
```

#### Détails d'un produit
```graphql
query Product($id: ID!) {
  product(id: $id) {
    id
    name
    mark
    priceVente
    priceAchat
    stock
    currency
    providerId
    provider {
      id
      name
      phone
      address
    }
    storeId
    store {
      id
      name
    }
    image
    createdAt
    updatedAt
  }
}
```

#### Créer un produit
```graphql
mutation CreateProduct($input: CreateProductInput!) {
  createProduct(input: $input) {
    id
    name
    mark
    priceVente
    priceAchat
    stock
    currency
    providerId
    storeId
    image
    createdAt
    updatedAt
  }
}
```

**Input:**
```kotlin
data class CreateProductInput(
    val name: String,
    val mark: String?,
    val priceVente: Double,
    val priceAchat: Double,
    val stock: Int,
    val currency: String,
    val providerId: String?,
    val storeId: String,
    val image: String? // URL ou Base64
)
```

#### Modifier un produit
```graphql
mutation UpdateProduct($id: ID!, $input: UpdateProductInput!) {
  updateProduct(id: $id, input: $input) {
    id
    name
    mark
    priceVente
    priceAchat
    stock
    currency
    providerId
    storeId
    image
    updatedAt
  }
}
```

#### Supprimer un produit
```graphql
mutation DeleteProduct($id: ID!) {
  deleteProduct(id: $id)
}
```

### Types Kotlin
```kotlin
data class Product(
    val id: String,
    val name: String,
    val mark: String?,
    val priceVente: Double,
    val priceAchat: Double,
    val stock: Int,
    val currency: String,
    val providerId: String?,
    val provider: Provider?,
    val storeId: String,
    val image: String?,
    val createdAt: String,
    val updatedAt: String
)

data class Provider(
    val id: String,
    val name: String,
    val phone: String,
    val address: String?,
    val storeId: String,
    val createdAt: String,
    val updatedAt: String
)
```

---

## 👥 Module Clients

### ✅ État d'implémentation
- ✅ Liste des clients (ClientsQuery probable)
- ✅ Création de client (CreateClientMutation)
- ✅ Notifications de succès (Snackbar)
- ✅ Rafraîchissement automatique après création
- ✅ Repository et DataSource

### Écrans requis (ADMIN uniquement)

#### 1. Liste des Clients (`ClientScreen`)
- **✅ État**: Implémenté avec notifications
- **Affichage**: Liste des clients
- **Recherche**: Par nom, téléphone
- **Notifications**:
  - ✅ Snackbar "Client ajouté avec succès" après création
  - ✅ Rafraîchissement automatique de la liste (OnRefreshClients)
- **Item**:
  - Nom du client
  - Téléphone
- **Bouton FAB**: "Nouveau client" (+)

#### 2. Détails Client (`ClientDetailScreen`)
- **Affichage**:
  - Nom
  - Téléphone
  - Nombre total de ventes
  - Montant total des achats
  - Dettes en cours (si applicable)
  - Historique des ventes (liste)
- **Actions**:
  - Bouton "Modifier"
  - Bouton "Supprimer"

#### 3. Nouveau Client (`NewClientScreen`)
- **Champs**:
  - Nom * (requis)
  - Téléphone * (requis)
- **Bouton**: "Créer le client"

#### 4. Modifier Client (`EditClientScreen`)
- Même formulaire que "Nouveau Client"
- Pré-rempli

### GraphQL Mutations & Queries

#### Liste des clients
```graphql
query Clients($storeId: String!) {
  clients(storeId: $storeId) {
    id
    name
    phone
    storeId
    createdAt
    updatedAt
  }
}
```

#### Détails client
```graphql
query Client($id: ID!) {
  client(id: $id) {
    id
    name
    phone
    storeId
    sales {
      id
      priceToPay
      currency
      date
    }
    debts {
      id
      totalAmount
      amountDue
      status
    }
    createdAt
    updatedAt
  }
}
```

#### Créer client
```graphql
mutation CreateClient($input: CreateClientInput!) {
  createClient(input: $input) {
    id
    name
    phone
    storeId
    createdAt
    updatedAt
  }
}
```

**Input:**
```kotlin
data class CreateClientInput(
    val name: String,
    val phone: String,
    val storeId: String
)
```

#### Modifier client
```graphql
mutation UpdateClient($id: ID!, $input: UpdateClientInput!) {
  updateClient(id: $id, input: $input) {
    id
    name
    phone
    updatedAt
  }
}
```

#### Supprimer client
```graphql
mutation DeleteClient($id: ID!) {
  deleteClient(id: $id)
}
```

### Types Kotlin
```kotlin
data class Client(
    val id: String,
    val name: String,
    val phone: String,
    val storeId: String,
    val createdAt: String,
    val updatedAt: String
)
```

---

## 💵 Module Caisse

### Écrans requis (ADMIN uniquement)

#### 1. Vue d'ensemble Caisse (`CaisseOverviewScreen`)
- **Affichage**:
  - Solde actuel (grand nombre)
  - Total entrées du jour/période
  - Total sorties du jour/période
  - Bénéfice net
  - Devise sélectionnée
- **Sélecteurs**:
  - Période (jour, semaine, mois, année)
  - Devise (USD, CDF)
- **Boutons**:
  - "Enregistrer une entrée" (bouton vert)
  - "Enregistrer une sortie" (bouton rouge)
  - "Transfert" (bouton bleu)
  - "Voir les transactions"

#### 2. Transactions Caisse (`CaisseTransactionsScreen`)
- **Affichage**: Liste des transactions
- **Filtres**:
  - Type (Entrée, Sortie, Toutes)
  - Période
  - Devise
- **Item**:
  ```
  ┌────────────────────────────────┐
  │ ⬆️ ENTREE        +150.00 USD   │
  │ Description: Vente produits    │
  │ Date: 20/12/2024 14:30         │
  └────────────────────────────────┘
  ```

#### 3. Nouvelle Entrée Caisse (`NewCaisseEntreeScreen`)
- **Champs**:
  - Montant * (number)
  - Devise * (USD ou CDF)
  - Description * (text)
  - Date (date picker)
- **Bouton**: "Enregistrer l'entrée"

#### 4. Nouvelle Sortie Caisse (`NewCaisseSortieScreen`)
- Même formulaire que "Entrée"
- **Bouton**: "Enregistrer la sortie"

#### 5. Transfert Caisse (`CaisseTransfertScreen`)
- **Champs**:
  - Montant * (number)
  - Devise * (USD ou CDF)
  - Description * (text)
  - Boutique destination * (dropdown)
  - Date (date picker)
- **Bouton**: "Effectuer le transfert"

### GraphQL Mutations & Queries

#### Vue d'ensemble caisse
```graphql
query Caisse($storeId: String!, $currency: String) {
  caisse(storeId: $storeId, currency: $currency) {
    currentBalance
    in
    out
    currency
    storeId
    store {
      id
      name
    }
  }
}
```

#### Liste des transactions
```graphql
query CaisseTransactions(
  $storeId: String!
  $period: String
  $currency: String
) {
  caisseTransactions(
    storeId: $storeId
    period: $period
    currency: $currency
  ) {
    id
    amount
    operation
    description
    currency
    date
    storeId
    createdAt
  }
}
```

#### Créer une entrée
```graphql
mutation CreateCaisseEntree($input: CreateCaisseTransactionInput!) {
  createCaisseEntree(input: $input) {
    id
    amount
    operation
    description
    currency
    date
    storeId
    createdAt
  }
}
```

#### Créer une sortie
```graphql
mutation CreateCaisseSortie($input: CreateCaisseTransactionInput!) {
  createCaisseSortie(input: $input) {
    id
    amount
    operation
    description
    currency
    date
    storeId
    createdAt
  }
}
```

**Input:**
```kotlin
data class CreateCaisseTransactionInput(
    val amount: Double,
    val description: String,
    val currency: String,
    val storeId: String,
    val date: String? // Format ISO 8601
)
```

### Types Kotlin
```kotlin
data class CaisseOverview(
    val currentBalance: Double,
    val `in`: Double, // Total entrées
    val out: Double, // Total sorties
    val currency: String,
    val storeId: String,
    val store: Store
)

data class CaisseTransaction(
    val id: String,
    val amount: Double,
    val operation: String, // "Entree" ou "Sortie"
    val description: String,
    val currency: String,
    val date: String,
    val storeId: String,
    val store: Store,
    val createdAt: String
)
```

---

## ⚙️ Module Paramètres

### Écrans requis (ADMIN uniquement)

#### 1. Paramètres Principaux (`SettingsScreen`)
- **Liste**:
  - Profil utilisateur
  - Sécurité (changer mot de passe)
  - Gestion des utilisateurs
  - Gestion des boutiques
  - Informations de l'entreprise
  - Abonnement (si applicable)
  - Aide
  - À propos
  - Déconnexion

#### 2. Profil Utilisateur (`ProfileScreen`)
- **Affichage**:
  - Photo de profil (avatar)
  - Nom
  - Téléphone
  - Rôle
  - Email (si disponible)
- **Actions**:
  - Modifier les informations

#### 3. Gestion des Utilisateurs (`UsersScreen`) - ADMIN
- **✅ État**: Entièrement implémenté
- **GraphQL**:
  - ✅ UsersQuery (liste complète)
  - ✅ CreateUserMutation
  - ✅ UpdateUserMutation
  - ✅ DeleteUserMutation
  - ✅ BlockUserMutation
  - ✅ UnblockUserMutation
- **Liste des utilisateurs (UserItem)**:
  - Nom (avec badge "(Bloqué)" si isBlocked)
  - Téléphone
  - Rôle (avec couleur: Cyan pour Admin, Yellow pour User)
  - Icône Lock pour les Admin
  - Menu dropdown (MoreVert icon)
- **Actions (DropdownMenu)**:
  - Bloquer (Lock icon) / Débloquer (Person icon)
  - Supprimer (Delete icon - rouge)
- **Notifications**:
  - ✅ Snackbar "Action réussie" après block/unblock/delete
  - ✅ Rafraîchissement automatique après action
- **Bouton FAB**: "Ajouter utilisateur"

#### 4. Gestion des Boutiques (`StoresManagementScreen`) - ADMIN
- **✅ GraphQL disponible**: CreateStore mutation
- **❌ UI non implémentée**
- **TODO - Liste des boutiques**:
  - Nom, adresse, téléphone
  - Company associée
- **Actions à implémenter**:
  - Voir détails
  - Modifier (UpdateStore)
  - Supprimer
- **Bouton FAB**: "Nouvelle boutique" (utilise CreateStore)

#### 5. Informations Entreprise (`CompanyInfoScreen`) - ADMIN
- **✅ GraphQL disponible**: 
  - CreateCompany mutation
  - CompanyQuery (pour lecture)
- **❌ UI non implémentée**
- **TODO - Affichage/Modification**:
  - Nom de l'entreprise *
  - Adresse *
  - Téléphone *
  - Email
  - Description *
  - Type *
  - Logo (URL)
  - RCCM (Registre de commerce)
  - ID National (idNat)
  - ID Commerce (idCommerce)
  - Liste des stores (lecture seule)
- **Validation**: name, address, phone, description, type sont requis

---

## 💾 Données et Types Communs

### Devises
```kotlin
enum class Currency(val symbol: String, val code: String) {
    USD("$", "USD"),
    CDF("FC", "CDF")
}
```

**IMPORTANT**: L'application ne doit supporter que **USD** et **CDF**. Aucune autre devise (EUR, XAF, XOF) ne doit être présente.

### Types de base
```kotlin
data class BaseEntity(
    val id: String,
    val createdAt: String,
    val updatedAt: String
)

data class User(
    val id: String, // ID utilisateur (ajouté récemment)
    val uid: String, // UID Firebase
    val name: String,
    val phone: String,
    val role: String, // "Admin" ou "User" (première lettre majuscule)
    val isBlocked: Boolean,
    val companyId: String?,
    val storeIds: List<String>?,
    val assignedStoreId: String?,
    val createdAt: String?,
    val updatedAt: String?
)
```

### Formatage des dates
- **Affichage**: `dd/MM/yyyy` (20/12/2024)
- **Affichage avec heure**: `dd/MM/yyyy HH:mm` (20/12/2024 14:30)
- **API (envoi)**: Format ISO 8601 (`yyyy-MM-dd'T'HH:mm:ss'Z'`)

### Formatage des montants
- **USD**: `150.00 $` ou `$150.00`
- **CDF**: `150.00 FC` ou `150,00 FC`
- Séparateur de milliers: espace ou virgule selon la locale

---

## 🎨 Design et UX

### Thème
- **Mode clair** par défaut
- **Mode sombre** (optionnel mais recommandé)
- **Couleurs principales**:
  - Primary: Bleu (#2563EB ou personnalisable)
  - Success: Vert (#10B981)
  - Error: Rouge (#EF4444)
  - Warning: Orange (#F59E0B)
  - Background: Blanc (#FFFFFF) / Sombre (#1F2937)

### Composants UI
- **Material Design 3** (Material You)
- **Cards** pour les listes d'items
- **FAB** (Floating Action Button) pour les actions principales
- **Bottom Navigation** pour la navigation principale
- **Top App Bar** avec titre et actions
- **Dialogs** pour les confirmations et formulaires rapides
- **Snackbar / Toast** pour les notifications
- **Loading indicators** pendant les requêtes
- **Empty states** avec illustrations quand pas de données
- **Error states** avec bouton de réessai

### Animations
- Transitions fluides entre écrans
- Animations d'apparition des listes (fade in)
- Skeleton loading pour les chargements
- Ripple effect sur les boutons

### Accessibilité
- Tailles de texte ajustables
- Contraste suffisant (WCAG AA)
- Content descriptions pour les icônes
- Support du TalkBack (lecteur d'écran Android)

---

## 🔧 Fonctionnalités Techniques

### Cache Offline (Optionnel mais recommandé)
- **Room Database** pour stocker localement:
  - Produits
  - Clients
  - Ventes récentes
- **Synchronisation** automatique quand connexion disponible
- **Indicator** de mode offline dans la UI

### Gestion d'erreurs
- **Erreurs réseau**: Afficher message + bouton réessayer
- **Erreurs API**: Afficher le message d'erreur du serveur
- **Erreurs de validation**: Afficher sous les champs concernés
- **Token expiré**: Refresh automatique ou redirection vers login

### Notifications Push (Optionnel)
- Firebase Cloud Messaging
- Notifications pour:
  - Nouvelle vente
  - Produit en rupture de stock
  - Dette payée

### Sécurité
- **Chiffrement** des tokens dans DataStore
- **HTTPS** obligatoire pour toutes les requêtes API
- **Validation** côté client ET serveur
- **Pas de données sensibles** dans les logs
- **Obfuscation** du code avec ProGuard/R8

### Performance
- **Pagination** pour les longues listes
- **Lazy loading** des images
- **Debounce** sur les recherches (500ms)
- **Cache** des requêtes GraphQL
- **Compression** des images avant upload

---

## 📋 Liste de Contrôle (Checklist)

### Authentification
- [ ] Écran de bienvenue
- [ ] Écran de connexion
- [ ] Écran d'inscription
- [ ] Gestion des tokens JWT
- [ ] Déconnexion

### Dashboard
- [ ] Statistiques des ventes
- [ ] État de la caisse
- [ ] Statistiques stock
- [ ] Sélecteurs période et devise
- [ ] Graphiques (optionnel)

### Ventes
- [ ] Liste paginée des ventes
- [ ] Filtres et recherche
- [ ] Nouvelle vente avec panier
- [ ] Détails d'une vente
- [ ] Génération de facture
- [ ] Impression PDF de facture
- [ ] Dialog auto après création de vente

### Produits
- [ ] Liste des produits
- [ ] Recherche et filtres
- [ ] Détails produit
- [ ] Nouveau produit (ADMIN)
- [ ] Modifier produit (ADMIN)
- [ ] Supprimer produit (ADMIN)
- [ ] Permissions USER (lecture seule)

### Clients (ADMIN)
- [ ] Liste des clients
- [ ] Détails client
- [ ] Nouveau client
- [ ] Modifier client
- [ ] Supprimer client

### Caisse (ADMIN)
- [ ] Vue d'ensemble
- [ ] Liste des transactions
- [ ] Nouvelle entrée
- [ ] Nouvelle sortie
- [ ] Transfert entre boutiques

### Paramètres (ADMIN)
- [ ] Profil utilisateur
- [ ] Gestion des utilisateurs
- [ ] Gestion des boutiques
- [ ] Informations entreprise
- [ ] Abonnement

### Système de permissions
- [ ] Vérification des permissions dans la UI
- [ ] Filtrage de la navigation selon le rôle
- [ ] Masquage des boutons selon les permissions
- [ ] Gestion des rôles ADMIN / USER

### Technique
- [ ] Apollo Client Android configuré
- [ ] Hilt injection de dépendances
- [ ] Room pour cache offline
- [ ] DataStore pour tokens
- [ ] Navigation Compose
- [ ] Gestion d'état avec StateFlow
- [ ] Gestion d'erreurs globale
- [ ] Loading states partout
- [ ] Génération PDF
- [ ] Support USD et CDF uniquement

---

## 📚 Ressources et Documentation

### API GraphQL
- **URL de base**: `https://rango-393061966089.europe-west1.run.app/query`
- **Authentification**: JWT Bearer token
- **Headers**: `Authorization: Bearer {accessToken}`

### Bibliothèques Recommandées

#### Core
```gradle
// Jetpack Compose
implementation("androidx.compose.ui:ui:1.5.4")
implementation("androidx.compose.material3:material3:1.1.2")
implementation("androidx.navigation:navigation-compose:2.7.5")

// Apollo GraphQL
implementation("com.apollographql.apollo3:apollo-runtime:3.8.2")

// Hilt
implementation("com.google.dagger:hilt-android:2.48")
kapt("com.google.dagger:hilt-compiler:2.48")

// Room
implementation("androidx.room:room-runtime:2.6.0")
kapt("androidx.room:room-compiler:2.6.0")
implementation("androidx.room:room-ktx:2.6.0")

// DataStore
implementation("androidx.datastore:datastore-preferences:1.0.0")

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// Image loading
implementation("io.coil-kt:coil-compose:2.5.0")

// PDF generation
implementation("com.itextpdf:itext7-core:7.2.5")
// ou utiliser Android PdfDocument natif
```

#### Optionnelles
```gradle
// Retrofit (si besoin en plus de GraphQL)
implementation("com.squareup.retrofit2:retrofit:2.9.0")

// Charting (graphiques)
implementation("com.github.PhilJay:MPAndroidChart:v3.1.0")

// Firebase
implementation(platform("com.google.firebase:firebase-bom:32.6.0"))
implementation("com.google.firebase:firebase-messaging-ktx")
```

### Structure du Projet (recommandée)
```
app/src/main/java/com/xshop/
├── data/
│   ├── api/
│   │   ├── ApolloClientProvider.kt
│   │   ├── mutations/
│   │   └── queries/
│   ├── local/
│   │   ├── AppDatabase.kt
│   │   ├── dao/
│   │   └── entities/
│   ├── repository/
│   │   ├── AuthRepository.kt
│   │   ├── ProductRepository.kt
│   │   ├── SaleRepository.kt
│   │   └── ...
│   └── datastore/
│       └── TokenManager.kt
├── domain/
│   ├── model/
│   │   ├── User.kt
│   │   ├── Product.kt
│   │   ├── Sale.kt
│   │   └── ...
│   └── usecase/
│       ├── auth/
│       ├── product/
│       └── sale/
├── ui/
│   ├── theme/
│   │   ├── Color.kt
│   │   ├── Theme.kt
│   │   └── Type.kt
│   ├── components/
│   │   ├── LoadingIndicator.kt
│   │   ├── ErrorView.kt
│   │   └── ...
│   ├── navigation/
│   │   └── AppNavigation.kt
│   └── screens/
│       ├── auth/
│       │   ├── WelcomeScreen.kt
│       │   ├── LoginScreen.kt
│       │   └── RegisterScreen.kt
│       ├── dashboard/
│       │   └── DashboardScreen.kt
│       ├── ventes/
│       │   ├── VentesListScreen.kt
│       │   ├── NewVenteScreen.kt
│       │   └── VenteDetailScreen.kt
│       ├── products/
│       │   ├── ProductsListScreen.kt
│       │   ├── ProductDetailScreen.kt
│       │   └── NewProductScreen.kt
│       └── ...
├── util/
│   ├── PermissionsManager.kt
│   ├── DateFormatters.kt
│   ├── CurrencyFormatters.kt
│   └── PdfGenerator.kt
└── XshopApplication.kt
```

---

## 🚀 Points d'Attention Importants

### 1. **Facture automatique après vente**
Après la création réussie d'une vente, l'application DOIT automatiquement:
1. Appeler la mutation `createFactureFromSale` avec l'ID de la vente créée
2. Afficher un Dialog contenant la facture formatée
3. Proposer un bouton "Imprimer" qui génère et partage le PDF
4. Permettre de fermer le dialog (retour à la liste des ventes)

### 2. **Génération PDF des factures**
- Utiliser **iText** ou **Android PdfDocument**
- Capturer le contenu du Dialog de facture
- Gérer les **multi-pages** si le contenu est trop long
- Bouton "Imprimer" doit:
  - Générer le PDF
  - Sauvegarder dans Downloads ou partager via Intent
  - Afficher un loader pendant la génération
  - Afficher un message de succès

### 3. **Gestion des tokens**
- **NE JAMAIS** envoyer de token pour Login, Register, Logout
- Pour toutes les autres requêtes, ajouter `Authorization: Bearer {token}`
- Utiliser un **Apollo Link** pour gérer cela automatiquement
- Exemple:
```kotlin
val authLink = object : HttpInterceptor {
    override suspend fun intercept(
        request: HttpRequest,
        chain: HttpInterceptorChain
    ): HttpResponse {
        val operation = request.operation
        
        // Ne pas ajouter de token pour ces opérations
        if (operation.name() in listOf("Login", "Register", "Logout")) {
            return chain.proceed(request)
        }
        
        // Ajouter le token pour toutes les autres opérations
        val token = tokenManager.getAccessToken()
        return chain.proceed(
            request.newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
        )
    }
}
```

### 4. **Devises uniquement USD et CDF**
- **Aucune autre devise** ne doit apparaître dans l'application
- Pas de EUR, XAF, XOF
- Tous les sélecteurs de devise doivent proposer uniquement:
  - USD ($)
  - CDF (FC)

### 5. **Permissions et rôles**
- Implémenter le système de permissions **dès le début**
- Toute action sensible doit vérifier la permission
- Exemples:
```kotlin
// Dans un composant
if (PermissionsManager.hasPermission(userRole, Permission.CREATE_PRODUCT)) {
    FloatingActionButton(onClick = { /* ... */ }) {
        Icon(Icons.Default.Add, "Nouveau produit")
    }
}

// Dans une navigation
if (PermissionsManager.hasPermission(userRole, Permission.VIEW_CAISSE)) {
    composable("caisse") { CaisseScreen() }
}
```

### 6. **Gestion d'erreurs réseau**
- Toujours afficher un message clair à l'utilisateur
- Proposer un bouton "Réessayer"
- Gérer les erreurs 401 (token expiré) → refresh ou logout
- Gérer les erreurs 403 (permission refusée) → message approprié
- Gérer les erreurs 500 (serveur) → "Une erreur s'est produite"

### 7. **UX et feedback utilisateur**
- **Loading states** partout (skeletons, spinners)
- **Empty states** avec illustrations
- **Success messages** après actions (Toast ou Snackbar)
- **Confirmation dialogs** pour les actions destructives (supprimer)
- **Pull to refresh** sur les listes

### 8. **Performance**
- **Pagination** obligatoire pour les listes longues (ventes, produits)
- **Lazy loading** des images
- **Debounce** sur les recherches
- **Cache** avec Apollo Client (InMemoryCache)
- **Optimistic UI** pour les actions rapides

### 9. **Validation côté client**
- Valider tous les formulaires avant envoi
- Afficher les erreurs sous les champs concernés
- Désactiver le bouton de soumission pendant l'envoi
- Exemples de validation:
  - Téléphone: format valide
  - Prix: > 0
  - Stock: ≥ 0
  - Champs requis: non vides

### 10. **Multi-boutiques**
- **Toujours** passer le `storeId` de la boutique active dans les requêtes
- Permettre de changer de boutique facilement (dropdown dans TopBar)
- Rafraîchir les données après changement de boutique

---

## 📝 Notes Finales

### ✅ État actuel du développement

**Phase 1 - COMPLÈTE**:
- ✅ Architecture MVVM + Clean Architecture
- ✅ GraphQL Apollo Client
- ✅ Hilt Dependency Injection
- ✅ Module Ventes (Sales) avec CRUD
- ✅ Liste des produits avec notifications
- ✅ Liste des clients avec notifications
- ✅ Gestion des utilisateurs (CRUD + Block/Unblock)

**Phase 2 - EN COURS**:
- ✅ Settings menu (liste flat)
- ✅ Company et Store creation (GraphQL)
- ⏳ Authentification (Login, Register, Logout) - schéma existe
- ⏳ Dashboard - à implémenter
- ⏳ Impression PDF factures - à implémenter
- ⏳ UI Company/Store management - à implémenter

**Phase 3 - À FAIRE**:
- ❌ Module Caisse (schéma GraphQL existe)
- ❌ Module Providers
- ❌ Filtres et recherches avancées
- ❌ Gestion des boutiques (UI)
- ❌ Profil utilisateur

**Phase 4 - OPTIONNEL**:
- ❌ Cache offline avec Room
- ❌ Mode sombre
- ❌ Notifications push
- ❌ Graphiques
- ❌ Export Excel/CSV

### Tests
- **Tests unitaires**: Repositories, UseCases, Formatters
- **Tests d'intégration**: API calls, Database
- **Tests UI**: Composables critiques (LoginScreen, NewVenteScreen)
- **Tests manuels**: Parcours utilisateur complets

### Livraison
- **Code source**: Repository Git
- **APK**: Version de production signée
- **Documentation**: README avec instructions d'installation
- **Credentials de test**: Comptes admin et user pour tester

### Support
- **Versions Android**: Min SDK 24 (Android 7.0) → Target SDK 34 (Android 14)
- **Écrans**: Téléphones et tablettes
- **Orientations**: Portrait prioritaire, landscape supporté
- **Langues**: Français (prioritaire), Anglais (optionnel)

---

## 🎬 Conclusion et État Actuel

Ce prompt reflète l'état actuel de l'application Android **RangoApp** développée avec Jetpack Compose.

### ✅ Ce qui est implémenté:
- Architecture MVVM + Clean Architecture avec Hilt
- Module Ventes (Sales) avec CRUD complet
- Module Produits avec notifications de succès
- Module Clients avec notifications de succès
- Module Utilisateurs avec CRUD + Block/Unblock
- Settings menu en liste flat
- GraphQL Apollo Client intégré
- Date format RFC3339
- Company et Store creation (backend prêt)

### ⏳ En cours / À compléter:
- Authentification (Login, Register)
- Dashboard avec statistiques
- UI pour gestion Company/Store
- Impression PDF des factures
- Module Caisse
- Module Providers
- Filtres et recherches avancés

### 📋 Prochaines étapes recommandées:
1. Implémenter l'authentification complète
2. Créer le Dashboard
3. Ajouter l'impression PDF des factures
4. Implémenter le module Caisse
5. Ajouter les filtres et recherches
6. Implémenter le cache offline (Room)

**Note importante**: Ce document est à jour avec les dernières modifications du code (décembre 2024), incluant les mutations `createCompany` et `createStore` récemment ajoutées.

Bon développement ! 🚀

