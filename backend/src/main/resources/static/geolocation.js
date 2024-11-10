if ("geolocation" in navigator) {
    let lastSentTime = 0;

    navigator.geolocation.watchPosition((position) => {
        const { latitude, longitude } = position.coords;
        const currentTime = Date.now();

        if (currentTime - lastSentTime >= 9000) {
            lastSentTime = currentTime;

            fetch('http://localhost:8888/location/capturelocation', {
                method: 'POST',
                headers: { 'Content-Type': 'application/json' },
                body: JSON.stringify({ latitude, longitude })
            })
            .then(response => {
                if (!response.ok) {
                    console.error("Failed to send location data");
                }
            })
            .catch(error => {
                console.error("Error sending location:", error);
            });
        }
    }, (error) => {
        console.error("Error obtaining location:", error);
    }, { enableHighAccuracy: true });
} else {
    console.log("Geolocation not available");
}
