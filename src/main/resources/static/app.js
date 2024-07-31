document.getElementById('loadDataBtn').addEventListener('click', loadData);
document.getElementById('searchBtn').addEventListener('click', searchProducts);

function loadData() {
    fetch('http://localhost:8080/api/data/load')
        .then(response => response.text())
        .then(data => {
            alert(data);
        })
        .catch(error => {
            console.error('Error loading data:', error);
            alert('Error loading data');
        });
}

function searchProducts() {
    const query = document.getElementById('searchQuery').value;
    fetch(`http://localhost:8080/api/search?query=${encodeURIComponent(query)}`)
        .then(response => response.json())
        .then(data => {
            displayResults(data);
        })
        .catch(error => {
            console.error('Error searching products:', error);
            alert('Error searching products');
        });
}

function displayResults(products) {
    const resultsDiv = document.getElementById('results');
    resultsDiv.innerHTML = '';

    if (products.length === 0) {
        resultsDiv.innerHTML = '<p>No products found</p>';
        return;
    }

    products.forEach(product => {
        const productDiv = document.createElement('div');
        productDiv.classList.add('product');
        productDiv.innerHTML = `
            <h2>${product.name}</h2>
            <p>${product.description}</p>
            <p>Price: $${product.price}</p>
            <p>Start Date: ${product.startDate}</p>
            <h3>SKUs:</h3>
        `;
        product.skus.forEach(sku => {
            const skuDiv = document.createElement('div');
            skuDiv.classList.add('sku');
            skuDiv.innerHTML = `
                <p>SKU Code: ${sku.skuCode}</p>
                <p>Quantity: ${sku.quantity}</p>
                <p>Color: ${sku.color}</p>
                <p>Available: ${sku.available}</p>
            `;
            productDiv.appendChild(skuDiv);
        });
        resultsDiv.appendChild(productDiv);
    });
}
