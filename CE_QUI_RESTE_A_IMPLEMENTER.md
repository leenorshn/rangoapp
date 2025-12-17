# 📋 Ce qui reste à implémenter - RangoApp

**Date** : 2024  
**Statut** : En cours de migration vers GraphQL

---

## 🔴 Priorité CRITIQUE - Migration GraphQL

### 1. **Data Sources GraphQL Manquantes**

#### ❌ GraphQLClientDataSource
- **Statut** : Non créé
- **Fichiers GraphQL disponibles** :
  - `ClientsQuery.graphql` ✅
  - `CreateClientMutation.graphql` ✅
- **À créer** : `app/src/main/java/com/avenir/rangoapp/data/datasource/GraphQLClientDataSource.kt`
- **Fonctions à implémenter** :
  - `getClients(storeId: String?)`
  - `createClient(name: String, phone: String, storeId: String)`
- **Repository** : ClientRepository n'existe pas encore

#### ❌ GraphQLProviderDataSource
- **Statut** : Non créé
- **Fichiers GraphQL disponibles** :
  - `ProvidersQuery.graphql` ✅
  - `CreateProviderMutation.graphql` ✅
- **À créer** : `app/src/main/java/com/avenir/rangoapp/data/datasource/GraphQLProviderDataSource.kt`
- **Fonctions à implémenter** :
  - `getProviders(storeId: String?)`
  - `createProvider(name: String, phone: String, address: String, storeId: String)`
- **Repository** : `ProviderRepositoryImpl` existe mais contient des TODOs

### 2. **Repositories Non Implémentés**

#### ❌ ProviderRepositoryImpl
- **Fichier** : `app/src/main/java/com/avenir/rangoapp/data/domaine/ProviderRepositoryImpl.kt`
- **Problème** : Contient des TODOs, retourne "Not implemented"
- **À faire** :
  - Injecter `GraphQLProviderDataSource`
  - Implémenter `createProvider()` et `getProviders()`
  - Mettre à jour `AppModule.kt` pour injecter la dépendance

#### ❌ ClientRepository
- **Statut** : N'existe pas
- **À créer** :
  - Interface : `app/src/main/java/com/avenir/rangoapp/data/repository/ClientRepository.kt`
  - Implémentation : `app/src/main/java/com/avenir/rangoapp/data/domaine/ClientRepositoryImpl.kt`
  - Injection dans `AppModule.kt`

---

## 🟡 Priorité MOYENNE - Fonctionnalités UI

### 3. **Écrans avec TODOs dans les onClick**

#### NewFactureScreen.kt (3 TODOs)
- **Ligne 73** : Bouton "Draft" - `onClick = { /*TODO*/ }`
- **Ligne 87** : Bouton "Save Invoice" - `onClick = { /*TODO*/ }`
- **Ligne 162** : Bouton "Customer" - `onClick = { /*TODO*/ }`
- **Action** : Implémenter la logique de sauvegarde de facture (draft/final) et sélection de client

#### TransferCaisseScreen.kt (2 TODOs)
- **Ligne 145** : Bouton de transfert - `onClick = { /*TODO*/ }`
- **Ligne 165** : Autre action - `onClick = { /*TODO*/ }`
- **Action** : Implémenter la logique de transfert de caisse

#### UsersScreen.kt
- **Ligne 78** : Card avec `onClick = { /*TODO*/ }`
- **Action** : Implémenter la navigation ou action sur la card utilisateur

#### PaymentScreen.kt
- **Ligne 82** : `onClick = { /*TODO*/ }`
- **Action** : Implémenter la logique de paiement

#### TransactionScreen.kt
- **Ligne 35** : Card avec `onClick = { /*TODO*/ }`
- **Action** : Implémenter l'affichage des détails de transaction

#### SortieScreen.kt
- **Ligne 132** : `onClick = { /*TODO*/ }`
- **Action** : Implémenter la logique de sortie de caisse

#### AccountCaisseScreen.kt
- **Ligne 58** : OutlinedCard avec `onClick = { /*TODO*/ }`
- **Action** : Implémenter l'action sur le compte de caisse

#### CaisseScreen.kt
- **Ligne 262** : Card avec `onClick = { /*TODO*/ }`
- **Action** : Implémenter l'action sur la caisse

#### RapportStoreItem.kt (Composant)
- **Ligne 55** : IconButton avec `onClick = { /*TODO*/ }`
- **Action** : Implémenter l'action sur l'item de rapport

#### ProductItem.kt (Composant)
- **Ligne 43** : IconButton avec `onClick = { /*TODO*/ }`
- **Action** : Implémenter l'action sur l'item produit (probablement édition/suppression)

### 4. **FactureViewModel**
- **Fichier** : `app/src/main/java/com/avenir/rangoapp/ui/screens/facture/facturation/newfacture/FactureViewModel.kt`
- **Problème** : Contient `TODO()` dans `OnSaveFacture`
- **Action** : Implémenter la logique complète de sauvegarde de facture

---

## 🟢 Priorité BASSE - Améliorations Code Quality

### 5. **Problèmes de Code (selon CODE_REVIEW.md)**

#### Hardcoded Values (21 occurrences)
- **Fichiers concernés** :
  - `CompanyDataSource.kt`
  - `UserDataSource.kt`
  - `ProductDataSource.kt`
  - `ProviderDataSource.kt`
  - `VenteDataSource.kt`
  - `RapportStoreDataSource.kt`
- **Action** : Extraire vers `Constants.kt` ou `BuildConfig`

#### Debug Code (14 occurrences)
- **Problème** : Utilisation de `println()` et `print()` au lieu de `Log.d/e()`
- **Fichiers concernés** :
  - `UserDataSource.kt`
  - `CompanyDataSource.kt`
  - `ProductDataSource.kt`
  - `RapportStoreDataSource.kt`
  - `CompanyRepositoryImpl.kt`
  - `HomeNavigation.kt`
- **Action** : Remplacer par `android.util.Log`

#### Gestion d'Erreurs Incomplète
- **Problème** : Exceptions catchées mais pas gérées correctement
- **Action** : Améliorer la gestion d'erreurs avec logging structuré

---

## 📊 Résumé par Priorité

### 🔴 CRITIQUE (À faire immédiatement)
1. ✅ Créer `GraphQLClientDataSource`
2. ✅ Créer `GraphQLProviderDataSource`
3. ✅ Implémenter `ProviderRepositoryImpl` avec GraphQL
4. ✅ Créer `ClientRepository` et `ClientRepositoryImpl`
5. ✅ Mettre à jour `AppModule.kt` pour injecter les nouvelles dépendances

### 🟡 MOYENNE (À faire bientôt)
1. ✅ Implémenter les TODOs dans `NewFactureScreen.kt`
2. ✅ Implémenter les TODOs dans `TransferCaisseScreen.kt`
3. ✅ Implémenter les TODOs dans les autres écrans UI
4. ✅ Compléter `FactureViewModel.OnSaveFacture`

### 🟢 BASSE (Nice to have)
1. ✅ Extraire les valeurs hardcodées
2. ✅ Remplacer `println()` par `Log`
3. ✅ Améliorer la gestion d'erreurs
4. ✅ Activer ProGuard pour la release
5. ✅ Ajouter des tests unitaires

---

## 📝 Notes Importantes

### Data Sources GraphQL Déjà Implémentées ✅
- ✅ `GraphQLAuthDataSource` (Login, Register, Logout, Me)
- ✅ `GraphQLStoreDataSource` (GetStores, CreateStore)
- ✅ `GraphQLProductDataSource` (GetProducts, CreateProduct, UpdateProduct, DeleteProduct)
- ✅ `GraphQLCompanyDataSource` (GetCompany, UpdateCompany)
- ✅ `GraphQLRapportStoreDataSource` (GetRapportStore, CreateRapportStore)
- ✅ `GraphQLUserDataSource` (GetUsers, CreateUser)
- ✅ `GraphQLFactureDataSource` (GetFactures, CreateFacture)

### Fichiers GraphQL Disponibles mais Non Utilisés
- ✅ `ClientsQuery.graphql` - Prêt à utiliser
- ✅ `CreateClientMutation.graphql` - Prêt à utiliser
- ✅ `ProvidersQuery.graphql` - Prêt à utiliser
- ✅ `CreateProviderMutation.graphql` - Prêt à utiliser

---

## 🎯 Plan d'Action Recommandé

### Phase 1 : Migration GraphQL (CRITIQUE)
1. Créer `GraphQLClientDataSource.kt`
2. Créer `GraphQLProviderDataSource.kt`
3. Implémenter `ProviderRepositoryImpl` avec GraphQL
4. Créer `ClientRepository` et `ClientRepositoryImpl`
5. Mettre à jour `AppModule.kt`
6. Tester les nouvelles fonctionnalités

### Phase 2 : Fonctionnalités UI (MOYENNE)
1. Implémenter la logique de facturation complète
2. Implémenter les actions de caisse
3. Implémenter les actions manquantes dans les écrans

### Phase 3 : Code Quality (BASSE)
1. Refactoring des valeurs hardcodées
2. Remplacement des `println()`
3. Amélioration de la gestion d'erreurs

---

**Dernière mise à jour** : Analyse basée sur le code actuel du projet



