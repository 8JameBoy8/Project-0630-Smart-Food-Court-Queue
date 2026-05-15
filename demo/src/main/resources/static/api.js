const API_BASE = 'http://localhost:8080';

async function apiCall(method, path, body = null, isFormData = false) {
    try {
        const options = { method };
        if (body && !isFormData) {
            options.headers = { 'Content-Type': 'application/json' };
            options.body = JSON.stringify(body);
        } else if (isFormData) {
            options.body = body;
        }
        const response = await fetch(API_BASE + path, options);
        const ct = response.headers.get('content-type') || '';
        if (ct.includes('application/json')) {
            return await response.json();
        }
        return await response.text();
    } catch (error) {
        console.error(`API Error [${method} ${path}]:`, error);
        throw error;
    }
}

const api = {
    login: (email, password) =>
        apiCall('POST', '/api/auth/login', { email, password }),
    register: (name, email, password, role) =>
        apiCall('POST', '/api/auth/register', { name, email, password, role }),

    getCanteens: () =>
        apiCall('GET', '/api/canteens'),
    getCanteenStores: (canteenId) =>
        apiCall('GET', `/api/canteens/${canteenId}/stores`),

    getTableStatus: (canteenId) =>
        apiCall('GET', `/api/canteens/${canteenId}/tables`),
    bookTable: (canteenId, userId, tableNo, duration) =>
        apiCall('POST', `/api/canteens/${canteenId}/book`, { userId, tableNo, duration }),
    cancelBooking: (reservationId) =>
        apiCall('POST', '/api/canteens/cancelBooked', { reservationId }),
    getCurrentReservation: (userId) =>
        apiCall('GET', `/api/users/${userId}/reservation`),
    getReservationHistory: (userId) =>
        apiCall('GET', `/api/users/${userId}/reservations`),

    getProducts: (storeId) =>
        apiCall('GET', `/api/stores/${storeId}/products`),

    createOrder: (userId, storeId) =>
        apiCall('POST', '/api/orders', { userId, storeId }),
    addItem: (orderId, productId, quantity, toppingIds) =>
        apiCall('POST', `/api/orders/${orderId}/items`, { productId, quantity, toppingIds }),
    setNote: (orderId, note) =>
        apiCall('POST', `/api/orders/${orderId}/note`, { note }),
    placeOrder: (orderId) =>
        apiCall('POST', `/api/orders/${orderId}/place`),
    cancelOrder: (orderId) =>
        apiCall('POST', `/api/orders/${orderId}/cancel`),
    getCurrentOrder: (userId) =>
        apiCall('GET', `/api/users/${userId}/order`),
    getOrderHistory: (userId) =>
        apiCall('GET', `/api/users/${userId}/orders`),

    uploadSlip: (paymentId, formData) =>
        apiCall('POST', `/api/payments/${paymentId}/upload`, formData, true),
    verifyPayment: (paymentId, isValid) =>
        apiCall('POST', `/api/payments/${paymentId}/verify`, { isValid }),

    getStaffStore: (staffId) =>
        apiCall('GET', `/api/staff/${staffId}/store`),
    getStaffOrders: (staffId) =>
        apiCall('GET', `/api/staff/${staffId}/orders`),
    setStoreStatus: (storeId, isOpen) =>
        apiCall('POST', `/api/stores/${storeId}/status`, { isOpen }),
    startCooking: (orderId) =>
        apiCall('POST', `/api/stores/orders/${orderId}/cooking`),
    markReady: (orderId) =>
        apiCall('POST', `/api/stores/orders/${orderId}/ready`),

    createStore: (storeName, staffId, canteenId) =>
        apiCall('POST', '/api/stores', { storeName, staffId, canteenId }),
    addProduct: (storeId, name, price) =>
        apiCall('POST', `/api/stores/${storeId}/products`, { name, price }),
    setProductStatus: (productId, isAvailable) =>
        apiCall('POST', `/api/products/${productId}/status`, { isAvailable }),
    addTopping: (productId, toppingName, extraPrice) =>
        apiCall('POST', `/api/products/${productId}/toppings`, { toppingName, extraPrice }),
    getToppings: (productId) =>
        apiCall('GET', `/api/products/${productId}/toppings`),
    setToppingStatus: (toppingId, isAvailable) =>
        apiCall('POST', `/api/products/toppings/${toppingId}/status`, { isAvailable }),
    getStoreOrders: (storeId) =>
        apiCall('GET', `/api/store/${storeId}/orders`),
};

function getSession() {
    return {
        userId: parseInt(localStorage.getItem('userId')) || null,
        userName: localStorage.getItem('userName') || '',
        userEmail: localStorage.getItem('userEmail') || '',
        userRole: localStorage.getItem('userRole') || '',
    };
}

function saveSession(data) {
    localStorage.setItem('userId', data.userId);
    localStorage.setItem('userName', data.name || '');
    localStorage.setItem('userEmail', data.email || '');
    localStorage.setItem('userRole', data.role || 'customer');
}

function clearSession() {
    ['userId', 'userName', 'userEmail', 'userRole',
     'selectedCanteenId', 'selectedCanteenName',
     'selectedStoreId', 'selectedStoreName',
     'selectedProduct', 'currentOrderId', 'currentPaymentId',
     'currentTotalPrice'].forEach(k => localStorage.removeItem(k));
}

function requireAuth() {
    const session = getSession();
    if (!session.userId) {
        window.location.href = 'index.html';
        return null;
    }
    return session;
}

function showToast(msg, type = 'info') {
    let toast = document.getElementById('toast');
    if (!toast) {
        toast = document.createElement('div');
        toast.id = 'toast';
        toast.style.cssText = `
            position:fixed; bottom:30px; left:50%; transform:translateX(-50%);
            padding:12px 24px; border-radius:25px; font-family:'Kanit',sans-serif;
            font-size:0.9rem; z-index:9999; transition:opacity 0.4s;
            box-shadow:0 4px 15px rgba(0,0,0,0.2); max-width:350px; text-align:center;
        `;
        document.body.appendChild(toast);
    }
    const colors = { info:'#333', success:'#2e7d32', error:'#c62828', warn:'#e65100' };
    toast.style.background = colors[type] || '#333';
    toast.style.color = '#fff';
    toast.style.opacity = '1';
    toast.innerText = msg;
    clearTimeout(toast._timer);
    toast._timer = setTimeout(() => { toast.style.opacity = '0'; }, 3000);
}

function setLoading(btn, loading) {
    if (loading) {
        btn._origText = btn.innerHTML;
        btn.innerHTML = '<i class="fa-solid fa-spinner fa-spin"></i>';
        btn.disabled = true;
    } else {
        btn.innerHTML = btn._origText || btn.innerHTML;
        btn.disabled = false;
    }
}
