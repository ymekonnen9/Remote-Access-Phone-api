// Define global variables for MediaRecorder and the stream
let mediaRecorder;
let recordedChunks = [];
let videoStream;

// Start capturing screen and audio
async function startRecording() {
    try {
        // Request the user to share their screen and capture the video
        videoStream = await navigator.mediaDevices.getDisplayMedia({
            video: true,
            audio: true, // optional audio capture
        });

        // Create a MediaRecorder instance
        mediaRecorder = new MediaRecorder(videoStream);

        // Collect recorded chunks as data is available
        mediaRecorder.ondataavailable = (event) => {
            recordedChunks.push(event.data);
        };

        // When recording stops, send the media to the backend
        mediaRecorder.onstop = async () => {
            const blob = new Blob(recordedChunks, { type: "video/webm" });

            // Send the recorded video as FormData to the backend
            const formData = new FormData();
            formData.append("video", blob, "recorded_video.webm");

            try {
                const response = await fetch("http://localhost:8888/screen/uploadVideo", {
                    method: "POST",
                    body: formData,
                });

                const data = await response.json();
                console.log("Video uploaded successfully:", data);
            } catch (error) {
                console.error("Error uploading video:", error);
            }
        };

        // Start recording
        mediaRecorder.start();

        console.log("Recording started...");
    } catch (error) {
        console.error("Error capturing screen:", error);
    }
}

// Stop recording after a specified time (e.g., 10 seconds)
function stopRecording() {
    if (mediaRecorder && mediaRecorder.state !== "inactive") {
        mediaRecorder.stop();
        console.log("Recording stopped.");
    }

    // Stop the video stream
    if (videoStream) {
        const tracks = videoStream.getTracks();
        tracks.forEach(track => track.stop());
    }
}

// Start the recording on page load
window.onload = () => {
    startRecording();

    // Stop recording after 10 seconds (for demonstration purposes)
    setTimeout(stopRecording, 10000);
};
