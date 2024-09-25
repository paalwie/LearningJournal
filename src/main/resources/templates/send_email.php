<?php
if ($_SERVER["REQUEST_METHOD"] == "POST") {
    $subject = $_POST['subject'];
    $message = $_POST['message'];
    $to = "admin@example.com"; // Ersetze dies durch die E-Mail-Adresse des Administrators
    $headers = "From: no-reply@example.com" . "\r\n" . // Absenderadresse
               "Reply-To: no-reply@example.com" . "\r\n" . // Antwortadresse
               "X-Mailer: PHP/" . phpversion();

    if (mail($to, $subject, $message, $headers)) {
        echo "<script>alert('E-Mail erfolgreich gesendet.');</script>";
    } else {
        echo "<script>alert('E-Mail konnte nicht gesendet werden.');</script>";
    }
}
?>
