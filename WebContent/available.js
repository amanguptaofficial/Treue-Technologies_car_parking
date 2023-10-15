function selectSpot(spotNumber) {
    const spot = document.querySelector('.parking-grid').children[spotNumber - 1];

    // Check if the spot is available before reserving it
    if (spot.classList.contains('available')) {
        spot.classList.remove('available');
        spot.classList.add('selected');

        const confirmation = confirm('Spot ' + spotNumber + ' selected. Proceed to reservation?');
        if (confirmation) {
            window.location.href = 'login.html';
        } else {
            alert('Spot ' + spotNumber + ' is not available');
        }

        // Toggle the background color of the selected spot
        if (spot.style.backgroundColor === 'green') {
            spot.style.backgroundColor = 'red';
        } else {
            spot.style.backgroundColor = 'green';
        }
    } else {
        alert('Spot ' + spotNumber + ' is not available');
    }
}
