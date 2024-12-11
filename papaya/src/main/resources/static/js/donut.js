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

// static/js/donut.js
document.addEventListener('DOMContentLoaded', function() {
    const scene = new THREE.Scene();
    const camera = new THREE.PerspectiveCamera(60, window.innerWidth / window.innerHeight, 0.1, 1000);
    const renderer = new THREE.WebGLRenderer({ alpha: true });
    renderer.setSize(window.innerWidth, window.innerHeight);

    const container = document.getElementById('three-container');
    if (container) {
        container.appendChild(renderer.domElement);
    }

    const geometry = new THREE.TorusGeometry(10, 3, 16, 100);
    const material = new THREE.MeshBasicMaterial({ color: 0xff6347, wireframe: true });
    const torus = new THREE.Mesh(geometry, material);

    // 도넛 위치 조정
    torus.position.y = 2; // Y축으로 5만큼 올림

    scene.add(torus);

    camera.position.z = 30;

    function animate() {
        requestAnimationFrame(animate);
        torus.rotation.x += 0.01;
        torus.rotation.y += 0.01;
        renderer.render(scene, camera);
    }
    animate();

    // 창 크기 변경 시 대응
    window.addEventListener('resize', function() {
        const width = window.innerWidth;
        const height = window.innerHeight;
        renderer.setSize(width, height);
        camera.aspect = width / height;
        camera.updateProjectionMatrix();
    });
});