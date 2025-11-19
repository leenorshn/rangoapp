# GraphQL Mutations - RangoApp

Toutes les mutations GraphQL disponibles dans l'API RangoApp.

**Note**: Toutes les mutations nécessitent une authentification (token JWT dans le header `Authorization: Bearer <token>`), sauf `login` et `register`.

---

## 🔐 Authentification

### Register - Créer un compte et une entreprise

```graphql
mutation {
  register(input: {
    email: "admin@example.com"
    password: "SecurePassword123!"
    name: "John Doe"
    phone: "+1234567890"
    companyName: "Ma Société"
    companyAddress: "123 Rue Example, Ville"
    companyPhone: "+1234567891"
    companyDescription: "Description de l'entreprise"
    companyType: "SARL"
    companyEmail: "contact@example.com"
    companyLogo: "https://example.com/logo.png"
    companyRccm: "RC123456"
    companyIdNat: "ID123456"
    companyIdCommerce: "COM123456"
    storeName: "Boutique Principale"
    storeAddress: "456 Avenue Example, Ville"
    storePhone: "+1234567892"
  }) {
    token
    user {
      id
      name
      email
      role
      companyId
    }
  }
}
```

### Login - Se connecter

```graphql
mutation {
  login(phone: "+1234567890", password: "SecurePassword123!") {
    token
    user {
      id
      name
      email
      phone
      role
      companyId
      storeIds
      assignedStoreId
    }
  }
}
```

### Logout - Se déconnecter

```graphql
mutation {
  logout
}
```

---

## 👥 Utilisateurs

### CreateUser - Créer un nouvel utilisateur

```graphql
mutation {
  createUser(input: {
    name: "Jane Doe"
    phone: "+1234567893"
    email: "jane@example.com"
    password: "SecurePassword123!"
    role: "User"
    storeId: "507f1f77bcf86cd799439011"
  }) {
    id
    uid
    name
    phone
    email
    role
    companyId
    assignedStoreId
    createdAt
  }
}
```

**Créer un Admin**:

```graphql
mutation {
  createUser(input: {
    name: "Admin User"
    phone: "+1234567894"
    email: "admin2@example.com"
    password: "SecurePassword123!"
    role: "Admin"
  }) {
    id
    name
    role
    companyId
    storeIds
  }
}
```

### UpdateUser - Mettre à jour un utilisateur

```graphql
mutation {
  updateUser(id: "507f1f77bcf86cd799439011", input: {
    name: "Jane Smith"
    phone: "+1234567895"
    email: "jane.smith@example.com"
    role: "User"
    storeId: "507f1f77bcf86cd799439012"
  }) {
    id
    name
    phone
    email
    role
    assignedStoreId
    updatedAt
  }
}
```

**Mise à jour partielle**:

```graphql
mutation {
  updateUser(id: "507f1f77bcf86cd799439011", input: {
    name: "Jane Updated"
  }) {
    id
    name
    updatedAt
  }
}
```

### DeleteUser - Supprimer un utilisateur

```graphql
mutation {
  deleteUser(id: "507f1f77bcf86cd799439011")
}
```

### BlockUser - Bloquer un utilisateur

```graphql
mutation {
  blockUser(id: "507f1f77bcf86cd799439011") {
    id
    name
    isBlocked
    updatedAt
  }
}
```

### UnblockUser - Débloquer un utilisateur

```graphql
mutation {
  unblockUser(id: "507f1f77bcf86cd799439011") {
    id
    name
    isBlocked
    updatedAt
  }
}
```

### AssignUserToStore - Assigner un utilisateur à une boutique

```graphql
mutation {
  assignUserToStore(userId: "507f1f77bcf86cd799439011", storeId: "507f1f77bcf86cd799439012") {
    id
    name
    role
    assignedStoreId
    storeIds
    updatedAt
  }
}
```

---

## 🏢 Entreprise

### UpdateCompany - Mettre à jour l'entreprise

```graphql
mutation {
  updateCompany(input: {
    name: "Nouveau Nom Société"
    address: "Nouvelle Adresse, Ville"
    phone: "+1234567896"
    email: "nouveau@example.com"
    description: "Nouvelle description"
    type: "SA"
    logo: "https://example.com/new-logo.png"
    rccm: "RC789012"
    idNat: "ID789012"
    idCommerce: "COM789012"
  }) {
    id
    name
    address
    phone
    email
    description
    type
    logo
    rccm
    idNat
    idCommerce
    updatedAt
  }
}
```

**Mise à jour partielle**:

```graphql
mutation {
  updateCompany(input: {
    name: "Nom Modifié"
    phone: "+1234567897"
  }) {
    id
    name
    phone
    updatedAt
  }
}
```

---

## 🏪 Boutiques (Stores)

### CreateStore - Créer une nouvelle boutique

```graphql
mutation {
  createStore(input: {
    name: "Boutique Secondaire"
    address: "789 Boulevard Example, Ville"
    phone: "+1234567898"
  }) {
    id
    name
    address
    phone
    companyId
    company {
      id
      name
    }
    createdAt
  }
}
```

### UpdateStore - Mettre à jour une boutique

```graphql
mutation {
  updateStore(id: "507f1f77bcf86cd799439011", input: {
    name: "Boutique Modifiée"
    address: "Nouvelle Adresse Boutique"
    phone: "+1234567899"
  }) {
    id
    name
    address
    phone
    updatedAt
  }
}
```

**Mise à jour partielle**:

```graphql
mutation {
  updateStore(id: "507f1f77bcf86cd799439011", input: {
    name: "Nouveau Nom Boutique"
  }) {
    id
    name
    updatedAt
  }
}
```

### DeleteStore - Supprimer une boutique

```graphql
mutation {
  deleteStore(id: "507f1f77bcf86cd799439011")
}
```

**Note**: La suppression échouera si la boutique contient des produits, clients ou factures.

---

## 📦 Produits

### CreateProduct - Créer un nouveau produit

```graphql
mutation {
  createProduct(input: {
    name: "Produit Exemple"
    mark: "Marque XYZ"
    priceVente: 15000.0
    priceAchat: 10000.0
    stock: 50.0
    storeId: "507f1f77bcf86cd799439011"
  }) {
    id
    name
    mark
    priceVente
    priceAchat
    stock
    storeId
    store {
      id
      name
    }
    createdAt
  }
}
```

### UpdateProduct - Mettre à jour un produit

```graphql
mutation {
  updateProduct(id: "507f1f77bcf86cd799439011", input: {
    name: "Produit Modifié"
    mark: "Nouvelle Marque"
    priceVente: 16000.0
    priceAchat: 11000.0
    stock: 45.0
  }) {
    id
    name
    mark
    priceVente
    priceAchat
    stock
    updatedAt
  }
}
```

**Mise à jour partielle (ex: ajuster le stock)**:

```graphql
mutation {
  updateProduct(id: "507f1f77bcf86cd799439011", input: {
    stock: 60.0
  }) {
    id
    name
    stock
    updatedAt
  }
}
```

### DeleteProduct - Supprimer un produit

```graphql
mutation {
  deleteProduct(id: "507f1f77bcf86cd799439011")
}
```

---

## 👤 Clients

### CreateClient - Créer un nouveau client

```graphql
mutation {
  createClient(input: {
    name: "Client Exemple"
    phone: "+1234567900"
    storeId: "507f1f77bcf86cd799439011"
  }) {
    id
    name
    phone
    storeId
    store {
      id
      name
    }
    createdAt
  }
}
```

### UpdateClient - Mettre à jour un client

```graphql
mutation {
  updateClient(id: "507f1f77bcf86cd799439011", input: {
    name: "Client Modifié"
    phone: "+1234567901"
  }) {
    id
    name
    phone
    updatedAt
  }
}
```

**Mise à jour partielle**:

```graphql
mutation {
  updateClient(id: "507f1f77bcf86cd799439011", input: {
    phone: "+1234567902"
  }) {
    id
    name
    phone
    updatedAt
  }
}
```

### DeleteClient - Supprimer un client

```graphql
mutation {
  deleteClient(id: "507f1f77bcf86cd799439011")
}
```

---

## 🏭 Fournisseurs (Providers)

### CreateProvider - Créer un nouveau fournisseur

```graphql
mutation {
  createProvider(input: {
    name: "Fournisseur Exemple"
    phone: "+1234567903"
    address: "123 Rue Fournisseur, Ville"
    storeId: "507f1f77bcf86cd799439011"
  }) {
    id
    name
    phone
    address
    storeId
    store {
      id
      name
    }
    createdAt
  }
}
```

### UpdateProvider - Mettre à jour un fournisseur

```graphql
mutation {
  updateProvider(id: "507f1f77bcf86cd799439011", input: {
    name: "Fournisseur Modifié"
    phone: "+1234567904"
    address: "Nouvelle Adresse Fournisseur"
  }) {
    id
    name
    phone
    address
    updatedAt
  }
}
```

**Mise à jour partielle**:

```graphql
mutation {
  updateProvider(id: "507f1f77bcf86cd799439011", input: {
    phone: "+1234567905"
  }) {
    id
    name
    phone
    updatedAt
  }
}
```

### DeleteProvider - Supprimer un fournisseur

```graphql
mutation {
  deleteProvider(id: "507f1f77bcf86cd799439011")
}
```

---

## 🧾 Factures

### CreateFacture - Créer une nouvelle facture

```graphql
mutation {
  createFacture(input: {
    products: [
      {
        productId: "507f1f77bcf86cd799439011"
        quantity: 2
        price: 15000.0
      },
      {
        productId: "507f1f77bcf86cd799439012"
        quantity: 1
        price: 25000.0
      }
    ]
    clientId: "507f1f77bcf86cd799439013"
    storeId: "507f1f77bcf86cd799439014"
    quantity: 3
    price: 55000.0
    currency: "XAF"
    date: "2024-11-19T10:00:00Z"
  }) {
    id
    factureNumber
    products {
      productId
      product {
        id
        name
        mark
      }
      quantity
      price
    }
    quantity
    date
    price
    currency
    client {
      id
      name
      phone
    }
    storeId
    store {
      id
      name
    }
    createdAt
  }
}
```

**Note**: 
- Le `factureNumber` est généré automatiquement (unique par boutique)
- Le stock des produits est automatiquement mis à jour (diminué)

### UpdateFacture - Mettre à jour une facture

```graphql
mutation {
  updateFacture(id: "507f1f77bcf86cd799439011", input: {
    products: [
      {
        productId: "507f1f77bcf86cd799439011"
        quantity: 3
        price: 15000.0
      }
    ]
    quantity: 3
    price: 45000.0
    currency: "XAF"
    date: "2024-11-19T11:00:00Z"
  }) {
    id
    factureNumber
    products {
      productId
      product {
        id
        name
      }
      quantity
      price
    }
    quantity
    price
    date
    updatedAt
  }
}
```

**Mise à jour partielle**:

```graphql
mutation {
  updateFacture(id: "507f1f77bcf86cd799439011", input: {
    price: 60000.0
    currency: "USD"
  }) {
    id
    factureNumber
    price
    currency
    updatedAt
  }
}
```

### DeleteFacture - Supprimer une facture

```graphql
mutation {
  deleteFacture(id: "507f1f77bcf86cd799439011")
}
```

**Note**: Le stock des produits est automatiquement restauré lors de la suppression.

---

## 📊 Rapports de Stock (RapportStore)

### CreateRapportStore - Créer un rapport de stock (entrée ou sortie)

**Entrée de stock**:

```graphql
mutation {
  createRapportStore(input: {
    productId: "507f1f77bcf86cd799439011"
    storeId: "507f1f77bcf86cd799439012"
    quantity: 10.0
    type: "entree"
    date: "2024-11-19T10:00:00Z"
  }) {
    id
    type
    product {
      id
      name
      mark
      stock
    }
    quantity
    date
    storeId
    store {
      id
      name
    }
    createdAt
  }
}
```

**Sortie de stock**:

```graphql
mutation {
  createRapportStore(input: {
    productId: "507f1f77bcf86cd799439011"
    storeId: "507f1f77bcf86cd799439012"
    quantity: 5.0
    type: "sortie"
    date: "2024-11-19T10:00:00Z"
  }) {
    id
    type
    product {
      id
      name
      stock
    }
    quantity
    date
    createdAt
  }
}
```

**Note**: 
- `type` doit être soit `"entree"` soit `"sortie"`
- Le stock du produit est automatiquement mis à jour:
  - `entree`: stock augmenté
  - `sortie`: stock diminué

### DeleteRapportStore - Supprimer un rapport de stock

```graphql
mutation {
  deleteRapportStore(id: "507f1f77bcf86cd799439011")
}
```

**Note**: Le stock du produit est automatiquement restauré lors de la suppression.

---

## 🔄 Mutations Combinées

### Exemple: Créer une facture complète avec vérification

```graphql
mutation CreateFactureComplete {
  # D'abord, vérifier le stock disponible
  product(id: "507f1f77bcf86cd799439011") {
    id
    name
    stock
  }
  
  # Créer la facture
  createFacture(input: {
    products: [
      {
        productId: "507f1f77bcf86cd799439011"
        quantity: 2
        price: 15000.0
      }
    ]
    clientId: "507f1f77bcf86cd799439013"
    storeId: "507f1f77bcf86cd799439014"
    quantity: 2
    price: 30000.0
    currency: "XAF"
    date: "2024-11-19T10:00:00Z"
  }) {
    id
    factureNumber
    price
    products {
      product {
        id
        name
        stock
      }
      quantity
    }
  }
}
```

---

## 📝 Notes Importantes

1. **Authentification**: 
   - `login` et `register` ne nécessitent PAS de token
   - Toutes les autres mutations nécessitent un token JWT valide

2. **Permissions**:
   - Les **Admin** peuvent créer/modifier/supprimer dans toutes les boutiques de leur entreprise
   - Les **User** ne peuvent créer/modifier/supprimer que dans leur boutique assignée

3. **Validation**:
   - Tous les champs requis doivent être fournis
   - Les emails et téléphones sont validés
   - Les IDs doivent être des ObjectIDs MongoDB valides

4. **Effets de bord**:
   - `createFacture`: Diminue automatiquement le stock des produits
   - `deleteFacture`: Restaure automatiquement le stock des produits
   - `createRapportStore`: Met à jour automatiquement le stock (entrée/sortie)
   - `deleteRapportStore`: Restaure automatiquement le stock

5. **Numérotation des factures**:
   - Le `factureNumber` est généré automatiquement et est unique par boutique
   - Format: Numéro séquentiel par boutique (ex: "FAC-001", "FAC-002", etc.)

6. **Dates**:
   - Utilisez le format ISO 8601: `"2024-11-19T10:00:00Z"`
   - Les dates sont stockées en UTC

