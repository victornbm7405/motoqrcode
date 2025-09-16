package br.com.fiap.mottu.motoqrcode.scanner;

import com.github.sarxos.webcam.Webcam;
import com.github.sarxos.webcam.WebcamPanel;
import com.github.sarxos.webcam.WebcamResolution;
import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.HybridBinarizer;
import org.json.JSONObject;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class QRCodeToggleScanner {

    public static void main(String[] args) throws Exception {

        Webcam webcam = Webcam.getDefault();
        if (webcam == null) {
            System.out.println("Nenhuma webcam detectada.");
            return;
        }

        webcam.setViewSize(WebcamResolution.VGA.getSize());

        WebcamPanel panel = new WebcamPanel(webcam);
        panel.setFPSDisplayed(true);
        panel.setMirrored(true);

        JFrame window = new JFrame("Scanner QR Code - Cadastro Mottu");
        window.add(panel);
        window.setResizable(true);
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.pack();
        window.setVisible(true);

        System.out.println("Aponte o QR Code da moto para a cÃ¢mera...");

        while (true) {
            BufferedImage image = webcam.getImage();
            if (image == null) continue;

            LuminanceSource source = new BufferedImageLuminanceSource(image);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));

            try {
                Map<DecodeHintType, Object> hints = new HashMap<>();
                hints.put(DecodeHintType.TRY_HARDER, Boolean.TRUE);

                Result result = new MultiFormatReader().decode(bitmap, hints);

                if (result != null) {
                    String qrContent = result.getText();
                    System.out.println("âœ… QR Code lido: " + qrContent);

                    // ðŸ‘‰ Interpretar o JSON lido
                    JSONObject json = new JSONObject(qrContent);

                    String placa = json.getString("placa");
                    String modelo = json.getString("modelo");

                    System.out.println("ðŸ“„ Dados extraÃ­dos:");
                    System.out.println("- Placa: " + placa);
                    System.out.println("- Modelo: " + modelo);

                    // ðŸ‘‰ teste requisiÃ§Ã£o POST
                    String jsonBody = String.format(
                            "{\"placa\":\"%s\", \"modelo\":\"%s\"}",
                            placa, modelo
                    );

                    HttpClient client = HttpClient.newHttpClient();

                    HttpRequest postRequest = HttpRequest.newBuilder()
                            .uri(URI.create("http://localhost:8080/motos/qrcode"))
                            .header("Content-Type", "application/json")
                            .POST(HttpRequest.BodyPublishers.ofString(jsonBody, StandardCharsets.UTF_8))
                            .build();

                    HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());

                    if (postResponse.statusCode() == 200 || postResponse.statusCode() == 201) {
                        System.out.println("âœ… Moto cadastrada com sucesso! Status: " + postResponse.statusCode());
                        JOptionPane.showMessageDialog(window,
                                "âœ… Moto cadastrada com sucesso!\nPlaca: " + placa + "\nModelo: " + modelo);
                    } else {
                        System.out.println("âŒ Erro ao cadastrar. Status: " + postResponse.statusCode());
                        JOptionPane.showMessageDialog(window,
                                "âŒ Erro ao cadastrar moto.\nStatus: " + postResponse.statusCode());
                    }

                    break; // Sai do loop apÃ³s ler e cadastrar
                }

            } catch (NotFoundException e) {
                // continuar procurando QR Code
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(window, "âŒ Erro: " + e.getMessage());
            }
        }

        webcam.close();
        window.dispose();
        System.out.println("ðŸ“¸ Scanner encerrado.");
    }
}
