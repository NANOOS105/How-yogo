document.addEventListener('DOMContentLoaded', function() {
	const images = document.querySelectorAll('.image');
	let currentIndex = 0;

	function showImage(index) {
		images.forEach((image, i) => {
			if (i === index) {
				image.style.display = 'block';
			} else {
				image.style.display = 'none';
			}
		});
	}

	showImage(currentIndex);

	document.addEventListener('click', function() {
		currentIndex = (currentIndex + 1) % images.length;
		showImage(currentIndex);
	});
});