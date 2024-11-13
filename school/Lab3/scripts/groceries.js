
// Array of products, each product is an object with different fieldset
// A set of ingredients should be added to products

var products = [

  {
    name: "Peanuts",
    LactoseFree: true,
    NutFree: false,
    organic: true,
    price: 0.99
  },
  {
    name: "Chocolate",
    LactoseFree: false,
    NutFree: false,
    organic: false,
    price: 1.99
  },
  {
    name: "Bread",
    LactoseFree: true,
    NutFree: false,
    organic: false,
    price: 2.35
  },
  {
    name: "Apples",
    LactoseFree: true,
    NutFree: true,
    organic: true,
    price: 2.50
  },
  {
    name: "Muffins",
    LactoseFree: true,
    NutFree: false,
    organic: false,
    price: 3.50
  },
  {
    name: "Swiss Cheese",
    LactoseFree: false,
    NutFree: true,
    organic: true,
    price: 4.99
  },
  {
    name: "Hamburger",
    LactoseFree: true,
    NutFree: true,
    organic: false,
    price: 6.00
  },

  {
    name: "Spaghetti",
    LactoseFree: true,
    NutFree: true,
    organic: false,
    price: 7.99
  },
  {
    name: "Pizza",
    LactoseFree: false,
    NutFree: true,
    organic: false,
    price: 10.99
  },
  {
    name: "Whole Rabbit",
    LactoseFree: true,
    NutFree: true,
    organic: true,
    price: 31.99
  }
];



// given restrictions provided, make a reduced list of products
// prices should be included in this list, as well as a sort based on price

function restrictListProducts(prods) {
  var LactoseFree = document.getElementById('LactoseFree').checked;
  var NutFree = document.getElementById('NutFree').checked;
  var organic = document.getElementById('organic').checked;
  let product_names = [];
  for (let i=0; i<prods.length; i+=1) {
    if (LactoseFree && NutFree) {
      if (prods[i].LactoseFree && prods[i].NutFree) {
        if (organic && prods[i].organic == true || !organic) {
          product_names.push(prods[i].name + ":  $" + prods[i].price);
        }
      }
    } else if (LactoseFree) {
      if (prods[i].LactoseFree) {
        if (organic && prods[i].organic == true || !organic) {
          product_names.push(prods[i].name + ":  $" + prods[i].price);
        }
      }
    } else if (NutFree) {
      if (prods[i].NutFree) {
        if (organic && prods[i].organic == true || !organic) {
          product_names.push(prods[i].name + ":  $" + prods[i].price);
        }
      }
    } else if (!LactoseFree && !NutFree) {
      if (organic && prods[i].organic == true || !organic) {
        product_names.push(prods[i].name + ":  $" + prods[i].price);
      }
    }


    // if ((LactoseFree == prods[i].LactoseFree)) {
    //     if (organic && prods[i].organic == true || !organic) {
    //       product_names.push(prods[i].name + ":  $" + prods[i].price);
    //     }
    // } else if ((NutFree == prods[i].NutFree)) {
    //     if (organic && prods[i].organic == true || !organic) {
    //       product_names.push(prods[i].name + ":  $" + prods[i].price);
    //     }
    // }



    // if ((restriction == "LactoseFree") && (prods[i].LactoseFree == true)){
    //   if (organic && prods[i].organic == true) {
    //     product_names.push(prods[i].name + ":  $" + prods[i].price);
    //   } else if (!organic) {
    //     product_names.push(prods[i].name + ":  $" + prods[i].price);
    //   }
    // }
    // else if ((restriction == "NutFree") && (prods[i].NutFree == true)){
    //   if (organic && prods[i].organic == true) {
    //     product_names.push(prods[i].name + ":  $" + prods[i].price);
    //   } else if (!organic) {
    //     product_names.push(prods[i].name + ":  $" + prods[i].price);
    //   }
    // }
    // else if (restriction == "None"){
    //   if (organic && prods[i].organic == true) {
    //     product_names.push(prods[i].name + ":  $" + prods[i].price);
    //   } else if (!organic) {
    //     product_names.push(prods[i].name + ":  $" + prods[i].price);
    //   }
    // }
  }
  return product_names;
}

// Calculate the total price of items, with received parameter being a list of products
function getTotalPrice(chosenProducts) {
totalPrice = 0;
for (let i=0; i<products.length; i+=1) {
  if (chosenProducts.indexOf(products[i].name) > -1){
    totalPrice += products[i].price;
  }
}
return totalPrice;
}
