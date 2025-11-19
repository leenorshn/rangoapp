# GraphQL Queries - RangoApp

Toutes les requêtes GraphQL disponibles dans l'API RangoApp.

**Note**: Toutes les requêtes nécessitent une authentification (token JWT dans le header `Authorization: Bearer <token>`), sauf indication contraire.

---

## 🔐 Authentification

### Me - Obtenir l'utilisateur connecté

```graphql
query {
  me {
    id
    uid
    name
    phone
    email
    role
    isBlocked
    companyId
    storeIds
    assignedStoreId
    createdAt
    updatedAt
  }
}
```

---

## 👥 Utilisateurs

### Users - Liste de tous les utilisateurs de l'entreprise

```graphql
query {
  users {
    id
    uid
    name
    phone
    email
    role
    isBlocked
    companyId
    storeIds
    assignedStoreId
    createdAt
    updatedAt
  }
}
```

### User - Obtenir un utilisateur par ID

```graphql
query {
  user(id: "507f1f77bcf86cd799439011") {
    id
    uid
    name
    phone
    email
    role
    isBlocked
    companyId
    storeIds
    assignedStoreId
    createdAt
    updatedAt
  }
}
```

---

## 🏢 Entreprise

### Company - Obtenir les informations de l'entreprise

```graphql
query {
  company {
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
    stores {
      id
      name
      address
      phone
      companyId
      createdAt
      updatedAt
    }
    createdAt
    updatedAt
  }
}
```

**Avec seulement les informations de base (sans stores)**:

```graphql
query {
  company {
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
    createdAt
    updatedAt
  }
}
```

---

## 🏪 Boutiques (Stores)

### Stores - Liste de toutes les boutiques accessibles

```graphql
query {
  stores {
    id
    name
    address
    phone
    companyId
    company {
      id
      name
      address
      phone
    }
    createdAt
    updatedAt
  }
}
```

**Note**: 
- Les **Admin** voient toutes les boutiques de leur entreprise
- Les **User** voient uniquement leur boutique assignée

### Store - Obtenir une boutique par ID

```graphql
query {
  store(id: "507f1f77bcf86cd799439011") {
    id
    name
    address
    phone
    companyId
    company {
      id
      name
      address
      phone
      email
      description
      type
    }
    createdAt
    updatedAt
  }
}
```

---

## 📦 Produits

### Products - Liste de tous les produits

```graphql
query {
  products {
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
      address
      phone
    }
    createdAt
    updatedAt
  }
}
```

### Products - Produits d'une boutique spécifique

```graphql
query {
  products(storeId: "507f1f77bcf86cd799439011") {
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
    updatedAt
  }
}
```

### Product - Obtenir un produit par ID

```graphql
query {
  product(id: "507f1f77bcf86cd799439011") {
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
      address
      phone
      company {
        id
        name
      }
    }
    createdAt
    updatedAt
  }
}
```

---

## 👤 Clients

### Clients - Liste de tous les clients

```graphql
query {
  clients {
    id
    name
    phone
    storeId
    store {
      id
      name
      address
    }
    createdAt
    updatedAt
  }
}
```

### Clients - Clients d'une boutique spécifique

```graphql
query {
  clients(storeId: "507f1f77bcf86cd799439011") {
    id
    name
    phone
    storeId
    store {
      id
      name
    }
    createdAt
    updatedAt
  }
}
```

### Client - Obtenir un client par ID

```graphql
query {
  client(id: "507f1f77bcf86cd799439011") {
    id
    name
    phone
    storeId
    store {
      id
      name
      address
      phone
      company {
        id
        name
      }
    }
    createdAt
    updatedAt
  }
}
```

---

## 🏭 Fournisseurs (Providers)

### Providers - Liste de tous les fournisseurs

```graphql
query {
  providers {
    id
    name
    phone
    address
    storeId
    store {
      id
      name
      address
    }
    createdAt
    updatedAt
  }
}
```

### Providers - Fournisseurs d'une boutique spécifique

```graphql
query {
  providers(storeId: "507f1f77bcf86cd799439011") {
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
    updatedAt
  }
}
```

### Provider - Obtenir un fournisseur par ID

```graphql
query {
  provider(id: "507f1f77bcf86cd799439011") {
    id
    name
    phone
    address
    storeId
    store {
      id
      name
      address
      phone
      company {
        id
        name
      }
    }
    createdAt
    updatedAt
  }
}
```

---

## 🧾 Factures

### Factures - Liste de toutes les factures

```graphql
query {
  factures {
    id
    factureNumber
    products {
      productId
      product {
        id
        name
        mark
        priceVente
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
      address
    }
    createdAt
    updatedAt
  }
}
```

### Factures - Factures d'une boutique spécifique

```graphql
query {
  factures(storeId: "507f1f77bcf86cd799439011") {
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
    createdAt
    updatedAt
  }
}
```

### Facture - Obtenir une facture par ID

```graphql
query {
  facture(id: "507f1f77bcf86cd799439011") {
    id
    factureNumber
    products {
      productId
      product {
        id
        name
        mark
        priceVente
        priceAchat
        stock
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
      store {
        id
        name
      }
    }
    storeId
    store {
      id
      name
      address
      phone
      company {
        id
        name
      }
    }
    createdAt
    updatedAt
  }
}
```

---

## 📊 Rapports de Stock (RapportStore)

### RapportStore - Liste de tous les rapports de stock

```graphql
query {
  rapportStore {
    id
    type
    product {
      id
      name
      mark
      priceVente
      stock
    }
    quantity
    date
    storeId
    store {
      id
      name
      address
    }
    createdAt
    updatedAt
  }
}
```

### RapportStore - Rapports d'une boutique spécifique

```graphql
query {
  rapportStore(storeId: "507f1f77bcf86cd799439011") {
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
    updatedAt
  }
}
```

### RapportStoreById - Obtenir un rapport par ID

```graphql
query {
  rapportStoreById(id: "507f1f77bcf86cd799439011") {
    id
    type
    product {
      id
      name
      mark
      priceVente
      priceAchat
      stock
      store {
        id
        name
      }
    }
    quantity
    date
    storeId
    store {
      id
      name
      address
      phone
      company {
        id
        name
      }
    }
    createdAt
    updatedAt
  }
}
```

---

## 🔍 Requêtes Combinées

### Exemple: Dashboard avec plusieurs données

```graphql
query Dashboard {
  me {
    id
    name
    role
    companyId
  }
  company {
    id
    name
    stores {
      id
      name
    }
  }
  stores {
    id
    name
  }
  products(storeId: "507f1f77bcf86cd799439011") {
    id
    name
    stock
    priceVente
  }
  factures(storeId: "507f1f77bcf86cd799439011") {
    id
    factureNumber
    price
    date
  }
}
```

---

## 📝 Notes Importantes

1. **Authentification**: Toutes les requêtes nécessitent un token JWT valide dans le header:
   ```
   Authorization: Bearer <votre-token-jwt>
   ```

2. **Permissions**:
   - Les **Admin** peuvent accéder à toutes les données de leur entreprise
   - Les **User** ne peuvent accéder qu'aux données de leur boutique assignée

3. **Paramètres optionnels**:
   - `storeId` est optionnel pour `products`, `clients`, `providers`, `factures`, et `rapportStore`
   - Si non fourni, retourne les données de toutes les boutiques accessibles

4. **Relations**:
   - Vous pouvez inclure les relations (store, company, etc.) dans vos requêtes
   - Attention à ne pas créer de requêtes trop profondes pour éviter les problèmes de performance

