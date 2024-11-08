package com.remoteAccess.backend.exception;

import com.remoteAccess.backend.exception.FileDeleteException;
import com.remoteAccess.backend.exception.FileDownloadException;
import com.remoteAccess.backend.exception.FileUploadException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(FileUploadException.class)
    public ResponseEntity<String> handleFileUploadException(FileUploadException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("File upload failed: " + ex.getMessage());
    }

    @ExceptionHandler(FileDownloadException.class)
    public ResponseEntity<String> handleFileDownloadException(FileDownloadException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("File download failed: " + ex.getMessage());
    }

    @ExceptionHandler(FileDeleteException.class)
    public ResponseEntity<String> handleFileDeleteException(FileDeleteException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("File deletion failed: " + ex.getMessage());
    }
}
