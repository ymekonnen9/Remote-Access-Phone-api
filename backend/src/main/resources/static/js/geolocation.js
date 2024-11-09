// src/main/resources/static/js/geolocation.js

if ("geolocation" in navigator) {
    let lastSentTime = 0; // Track the last time the location was sent

    navigator.geolocation.watchPosition((position) => {
        const { latitude, longitude } = position.coords;
        const currentTime = Date.now();

        // Check if 9 seconds (9000 milliseconds) have passed since the last sent time
        if (currentTime - lastSentTime >= 9000) {
            // Update lastSentTime to the current time
            lastSentTime = currentTime;

            // Send location data to backend
            fetch('/location', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ latitude, longitude })
            }).then(response => {
                if (!response.ok) {
                    console.error("Failed to send location data");
                }
            }).catch(error => {
                console.error("Error sending location:", error);
            });
        }
    }, (error) => {
        console.error("Error obtaining location:", error);
    }, { enableHighAccuracy: true });
} else {
    console.log("Geolocation not available");
}
