document.addEventListener('DOMContentLoaded', function() {
    // Initialize Lucide icons
    lucide.createIcons();

    // Top Ranked Restaurants
    const topRestaurants = [
        { id: 1, name: "맛있는 파스타", rating: 4.8, image: "/img/restaurant1.png", cuisine: "이탈리안" },
        { id: 2, name: "황금 돈까스", rating: 4.7, image: "/img/restaurant2.png", cuisine: "일식" },
        { id: 3, name: "신선한 초밥", rating: 4.6, image: "/img/restaurant3.png", cuisine: "일식" },
        { id: 4, name: "화덕 피자", rating: 4.5, image: "/img/restaurant4.png", cuisine: "이탈리안" },
        { id: 5, name: "매콤한 떡볶이", rating: 4.4, image: "/img/restaurant5.png", cuisine: "한식" },
    ];

    const topRestaurantsSlider = document.getElementById('topRestaurantsSlider');
    let currentIndex = 0;

    function renderTopRestaurants() {
        topRestaurantsSlider.innerHTML = topRestaurants.map(restaurant => `
            <div class="w-full flex-shrink-0 px-4">
                <div class="bg-white rounded-lg shadow-xl overflow-hidden">
                    <img src="${restaurant.image}" alt="${restaurant.name}" class="w-full h-64 object-cover">
                    <div class="p-6">
                        <h3 class="text-2xl font-semibold mb-2">${restaurant.name}</h3>
                        <p class="text-gray-600 mb-2">${restaurant.cuisine}</p>
                        <div class="flex items-center">
                            <i data-lucide="star" class="text-yellow-400 mr-1"></i>
                            <span class="text-xl font-bold">${restaurant.rating}</span>
                        </div>
                    </div>
                </div>
            </div>
        `).join('');
        lucide.createIcons();
    }

    renderTopRestaurants();

    document.getElementById('prevSlide').addEventListener('click', () => {
        currentIndex = (currentIndex - 1 + topRestaurants.length) % topRestaurants.length;
        topRestaurantsSlider.style.transform = `translateX(-${currentIndex * 100}%)`;
    });

    document.getElementById('nextSlide').addEventListener('click', () => {
        currentIndex = (currentIndex + 1) % topRestaurants.length;
        topRestaurantsSlider.style.transform = `translateX(-${currentIndex * 100}%)`;
    });

    // Recommended Restaurants
    const recommendedRestaurants = [
        { id: 1, name: "향긋한 커피", category: "카페", image: "/img/cafe.png" },
        { id: 2, name: "매콤한 떡볶이", category: "분식", image: "/img/tteokbokki.png" },
        { id: 3, name: "건강한 샐러드", category: "건강식", image: "/img/salad.png" },
        { id: 4, name: "풍미 가득 피자", category: "양식", image: "/img/pizza.png" },
        { id: 5, name: "신선한 회", category: "일식", image: "/img/sashimi.png" },
        { id: 6, name: "든든한 백반", category: "한식", image: "/img/korean-set-meal.png" },
        { id: 7, name: "달콤한 디저트", category: "디저트", image: "/img/dessert.png" },
        { id: 8, name: "향긋한 차", category: "카페", image: "/img/tea.png" },
    ];

    const categories = ["전체", "카페", "분식", "건강식", "양식", "일식", "한식", "디저트"];
    let currentFilter = "전체";

    const categoryButtons = document.getElementById('categoryButtons');
    const restaurantGrid = document.getElementById('restaurantGrid');

    function renderCategoryButtons() {
        categoryButtons.innerHTML = categories.map(category => `
            <button
                class="mx-2 px-4 py-2 rounded-full ${currentFilter === category ? 'bg-orange-500 text-white' : 'bg-gray-200 text-gray-800'}"
                data-category="${category}"
            >
                ${category}
            </button>
        `).join('');

        categoryButtons.querySelectorAll('button').forEach(button => {
            button.addEventListener('click', () => {
                currentFilter = button.dataset.category;
                renderCategoryButtons();
                renderRecommendedRestaurants();
            });
        });
    }

    function renderRecommendedRestaurants() {
        const filteredRestaurants = currentFilter === "전체"
            ? recommendedRestaurants
            : recommendedRestaurants.filter(restaurant => restaurant.category === currentFilter);

        restaurantGrid.innerHTML = filteredRestaurants.map(restaurant => `
            <div class="bg-white rounded-lg shadow-xl overflow-hidden">
                <img src="${restaurant.image}" alt="${restaurant.name}" class="w-full h-48 object-cover">
                <div class="p-4">
                    <h3 class="text-xl font-semibold mb-2">${restaurant.name}</h3>
                    <p class="text-gray-600">${restaurant.category}</p>
                </div>
            </div>
        `).join('');
    }

    renderCategoryButtons();
    renderRecommendedRestaurants();
});
// const scene = new THREE.Scene();
// const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
// const renderer = new THREE.WebGLRenderer({ alpha: true });
// renderer.setSize(window.innerWidth, window.innerHeight);
// document.getElementById('three-container').appendChild(renderer.domElement);
//
// const geometry = new THREE.TorusGeometry(10, 3, 16, 100);
// const material = new THREE.MeshBasicMaterial({ color: 0xff6347, wireframe: true });
// const torus = new THREE.Mesh(geometry, material);
// scene.add(torus);
//
// camera.position.z = 30;
//
// function animate() {
//     requestAnimationFrame(animate);
//     torus.rotation.x += 0.01;
//     torus.rotation.y += 0.01;
//     renderer.render(scene, camera);
// }
// animate();





// document.addEventListener('DOMContentLoaded', function() {
//     const topRestaurants = [
//         { id: 1, name: "맛있는 파스타", rating: 4.8, image: "/images/restaurant1.jpg", cuisine: "이탈리안" },
//         { id: 2, name: "황금 돈까스", rating: 4.7, image: "/images/restaurant2.jpg", cuisine: "일식" },
//         { id: 3, name: "신선한 초밥", rating: 4.6, image: "/images/restaurant3.jpg", cuisine: "일식" },
//         { id: 4, name: "화덕 피자", rating: 4.5, image: "/images/restaurant4.jpg", cuisine: "이탈리안" },
//         { id: 5, name: "매콤한 떡볶이", rating: 4.4, image: "/images/restaurant5.jpg", cuisine: "한식" },
//     ];
//
//     const recommendedRestaurants = [
//         { id: 1, name: "향긋한 커피", category: "카페", image: "/images/cafe.jpg" },
//         { id: 2, name: "매콤한 떡볶이", category: "분식", image: "/images/tteokbokki.jpg" },
//         { id: 3, name: "건강한 샐러드", category: "건강식", image: "/images/salad.jpg" },
//         { id: 4, name: "풍미 가득 피자", category: "양식", image: "/images/pizza.jpg" },
//         { id: 5, name: "신선한 회", category: "일식", image: "/images/sashimi.jpg" },
//         { id: 6, name: "든든한 백반", category: "한식", image: "/images/korean-set-meal.jpg" },
//         { id: 7, name: "달콤한 디저트", category: "디저트", image: "/images/dessert.jpg" },
//         { id: 8, name: "향긋한 차", category: "카페", image: "/images/tea.jpg" },
//     ];
//
//     const categories = ["전체", "카페", "분식", "건강식", "양식", "일식", "한식", "디저트"];
//
//     // Top Ranked Restaurants Slider
//     const sliderContainer = document.getElementById('topRestaurantsSlider');
//     let currentIndex = 0;
//
//     function renderTopRestaurants() {
//         sliderContainer.innerHTML = topRestaurants.map(restaurant => `
//             <div class="w-full flex-shrink-0 px-4">
//                 <div class="bg-white rounded-lg shadow-xl overflow-hidden restaurant-card">
//                     <img src="${restaurant.image}" alt="${restaurant.name}" class="w-full h-64 object-cover">
//                     <div class="p-6">
//                         <h3 class="text-2xl font-semibold mb-2">${restaurant.name}</h3>
//                         <p class="text-gray-600 mb-2">${restaurant.cuisine}</p>
//                         <div class="flex items-center">
//                             <i class="fas fa-star text-yellow-400 mr-1"></i>
//                             <span class="text-xl font-bold">${restaurant.rating}</span>
//                         </div>
//                     </div>
//                 </div>
//             </div>
//         `).join('');
//     }
//
//     renderTopRestaurants();
//
//     document.getElementById('prevSlide').addEventListener('click', () => {
//         currentIndex = (currentIndex - 1 + topRestaurants.length) % topRestaurants.length;
//         sliderContainer.style.transform = `translateX(-${currentIndex * 100}%)`;
//     });
//
//     document.getElementById('nextSlide').addEventListener('click', () => {
//         currentIndex = (currentIndex + 1) % topRestaurants.length;
//         sliderContainer.style.transform = `translateX(-${currentIndex * 100}%)`;
//     });
//
//     // Recommended Restaurants
//     const categoryButtonsContainer = document.getElementById('categoryButtons');
//     const recommendedRestaurantsContainer = document.getElementById('recommendedRestaurants');
//     let currentFilter = "전체";
//
//     function renderCategoryButtons() {
//         categoryButtonsContainer.innerHTML = categories.map(category => `
//             <button class="mx-2 px-4 py-2 rounded-full ${
//             category === currentFilter ? 'bg-orange-500 text-white' : 'bg-gray-200 text-gray-800'
//         }" data-category="${category}">
//                 ${category}
//             </button>
//         `).join('');
//
//         categoryButtonsContainer.querySelectorAll('button').forEach(button => {
//             button.addEventListener('click', () => {
//                 currentFilter = button.dataset.category;
//                 renderCategoryButtons();
//                 renderRecommendedRestaurants();
//             });
//         });
//     }
//
//     function renderRecommendedRestaurants() {
//         const filteredRestaurants = currentFilter === "전체"
//             ? recommendedRestaurants
//             : recommendedRestaurants.filter(restaurant => restaurant.category === currentFilter);
//
//         recommendedRestaurantsContainer.innerHTML = filteredRestaurants.map(restaurant => `
//             <div class="bg-white rounded-lg shadow-xl overflow-hidden restaurant-card">
//                 <img src="${restaurant.image}" alt="${restaurant.name}" class="w-full h-48 object-cover">
//                 <div class="p-4">
//                     <h3 class="text-xl font-semibold mb-2">${restaurant.name}</h3>
//                     <p class="text-gray-600">${restaurant.category}</p>
//                 </div>
//             </div>
//         `).join('');
//     }
//
//     renderCategoryButtons();
//     renderRecommendedRestaurants();
// });













// document.addEventListener('DOMContentLoaded', function() {
//     // Dark mode toggle
//     const darkModeToggle = document.createElement('button');
//     darkModeToggle.innerHTML = '🌙';
//     darkModeToggle.className = 'fixed top-4 right-4 z-50 p-2 rounded-full bg-background-light dark:bg-background-dark';
//     document.body.appendChild(darkModeToggle);
//
//     darkModeToggle.addEventListener('click', () => {
//         document.body.classList.toggle('dark');
//         darkModeToggle.innerHTML = document.body.classList.contains('dark') ? '☀️' : '🌙';
//     });
//
//     // Parallax effect
//     const heroBackground = document.getElementById('hero-background');
//     window.addEventListener('scroll', () => {
//         const scrollPosition = window.pageYOffset;
//         heroBackground.style.transform = `translateY(${scrollPosition * 0.5}px)`;
//     });
//
//     // Three.js animation
//     const scene = new THREE.Scene();
//     const camera = new THREE.PerspectiveCamera(75, window.innerWidth / window.innerHeight, 0.1, 1000);
//     const renderer = new THREE.WebGLRenderer({ alpha: true });
//     renderer.setSize(window.innerWidth, window.innerHeight);
//     document.getElementById('three-container').appendChild(renderer.domElement);
//
//     const geometry = new THREE.TorusGeometry(10, 3, 16, 100);
//     const material = new THREE.MeshBasicMaterial({ color: 0xff6347, wireframe: true });
//     const torus = new THREE.Mesh(geometry, material);
//     scene.add(torus);
//
//     camera.position.z = 30;
//
//     function animate() {
//         requestAnimationFrame(animate);
//         torus.rotation.x += 0.01;
//         torus.rotation.y += 0.01;
//         renderer.render(scene, camera);
//     }
//     animate();
//
//     // Top Ranked Restaurants
//     const topRestaurants = [
//         { id: 1, name: "맛있는 파스타", rating: 4.8, image: "https://via.placeholder.com/800x500", cuisine: "이탈리안" },
//         { id: 2, name: "황금 돈까스", rating: 4.7, image: "https://via.placeholder.com/800x500", cuisine: "일식" },
//         { id: 3, name: "신선한 초밥", rating: 4.6, image: "https://via.placeholder.com/800x500", cuisine: "일식" },
//         { id: 4, name: "화덕 피자", rating: 4.5, image: "https://via.placeholder.com/800x500", cuisine: "이탈리안" },
//         { id: 5, name: "매콤한 떡볶이", rating: 4.4, image: "https://via.placeholder.com/800x500", cuisine: "한식" },
//     ];
//
//     const carousel = document.getElementById('restaurant-carousel');
//     topRestaurants.forEach(restaurant => {
//         const card = document.createElement('div');
//         card.className = 'w-full flex-shrink-0 px-4';
//         card.innerHTML = `
//             <div class="bg-background-light dark:bg-background-dark rounded-lg overflow-hidden shadow-neumorphism-light dark:shadow-neumorphism-dark">
//                 <img src="${restaurant.image}" alt="${restaurant.name}" class="w-full h-64 object-cover">
//                 <div class="p-6">
//                     <h3 class="text-2xl font-semibold mb-2 text-text dark:text-text-dark">${restaurant.name}</h3>
//                     <p class="text-text-light dark:text-text-dark mb-2">${restaurant.cuisine}</p>
//                     <div class="flex items-center">
//                         <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5 text-yellow-400" viewBox="0 0 20 20" fill="currentColor">
//                             <path d="M9.049 2.927c.3-.921 1.603-.921 1.902 0l1.07 3.292a1 1 0 00.95.69h3.462c.969 0 1.371 1.24.588 1.81l-2.8 2.034a1 1 0 00-.364 1.118l1.07 3.292c.3.921-.755 1.688-1.54 1.118l-2.8-2.034a1 1 0 00-.364 1.118l-1.07 3.292c-.3.921-1.603.921-1.902 0l-1.07-3.292a1 1 0 00-.364-1.118l-2.8-2.034c-.783-.57-.38-1.81.588-1.81h3.462a1 1 0 00.95-.69l1.07-3.292z" />
//                         </svg>
//                         <span class="text-xl font-bold text-text dark:text-text-dark ml-1">${restaurant.rating}</span>
//                     </div>
//                 </div>
//             </div>
//         `;
//         carousel.appendChild(card);
//     });
//
//     let currentIndex = 0;
//     const prevButton = document.getElementById('prev-slide');
//     const nextButton = document.getElementById('next-slide');
//
//     prevButton.addEventListener('click', () => {
//         currentIndex = (currentIndex - 1 + topRestaurants.length) % topRestaurants.length;
//         updateCarousel();
//     });
//
//     nextButton.addEventListener('click', () => {
//         currentIndex = (currentIndex + 1) % topRestaurants.length;
//         updateCarousel();
//     });
//
//     function updateCarousel() {
//         carousel.style.transform = `translateX(-${currentIndex * 100}%)`;
//     }
//
//     // Recommended Restaurants
//     const recommendedRestaurants = [
//         { id: 1, name: "향긋한 커피", category: "카페", image: "https://via.placeholder.com/400x300" },
//         { id: 2, name: "매콤한 떡볶이", category: "분식", image: "https://via.placeholder.com/400x300" },
//         { id: 3, name: "건강한 샐러드", category: "건강식", image: "https://via.placeholder.com/400x300" },
//         { id: 4, name: "풍미 가득 피자", category: "양식", image: "https://via.placeholder.com/400x300" },
//         { id: 5, name: "신선한 회", category: "일식", image: "https://via.placeholder.com/400x300" },
//         { id: 6, name: "든든한 백반", category: "한식", image: "https://via.placeholder.com/400x300" },
//         { id: 7, name: "달콤한 디저트", category: "디저트", image: "https://via.placeholder.com/400x300" },
//         { id: 8, name: "향긋한 차", category: "카페", image: "https://via.placeholder.com/400x300" },
//     ];
//
//     const categories = ["전체", "카페", "분식", "건강식", "양식", "일식", "한식", "디저트"];
//     const categoryFilters = document.getElementById('category-filters');
//     const restaurantGrid = document.getElementById('restaurant-grid');
//
//     categories.forEach(category => {
//         const button = document.createElement('button');
//         button.textContent = category;
//         button.className = 'category-button mx-2 px-4 py-2 rounded-full bg-gray-200 text-gray-800';
//         button.addEventListener('click', () => filterRestaurants(category));
//         categoryFilters.appendChild(button);
//     });
//
//     function filterRestaurants(category) {
//         const buttons = categoryFilters.getElementsByClassName('category-button');
//         Array.from(buttons).forEach(button => {
//             button.classList.toggle('active', button.textContent === category);
//         });
//
//         restaurantGrid.innerHTML = '';
//         const filteredRestaurants = category === "전체"
//             ? recommendedRestaurants
//             : recommendedRestaurants.filter(restaurant => restaurant.category === category);
//
//         filteredRestaurants.forEach(restaurant => {
//             const card = document.createElement('div');
//             card.className = 'restaurant-card bg-white dark:bg-gray-800 rounded-lg shadow-xl overflow-hidden';
//             card.innerHTML = `
//                 <img src="${restaurant.image}" alt="${restaurant.name}" class="w-full h-48 object-cover">
//                 <div class="p-4">
//                     <h3 class="text-xl font-semibold mb-2 text-text dark:text-text-dark">${restaurant.name}</h3>
//                     <p class="text-text-light dark:text-text-dark">${restaurant.category}</p>
//                 </div>
//             `;
//             restaurantGrid.appendChild(card);
//         });
//     }
//
//     // Initial load
//     filterRestaurants("전체");
// });