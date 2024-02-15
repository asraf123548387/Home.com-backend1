package com.example.demo.Service;

import com.example.demo.dto.BookingDto;
import com.example.demo.entity.Booking;
import com.example.demo.entity.Room;
import com.example.demo.entity.User;
import com.example.demo.repo.BookingRepo;
import com.example.demo.repo.RoomRepo;
import com.example.demo.repo.UserRepo;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class BookingService {
    @Autowired
  private  RoomRepo roomRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    private BookingRepo bookingRepo;

    @Autowired
    private JavaMailSender javaMailSender;

    @Transactional
    public void bookRoom(BookingDto bookingDto) throws IOException, MessagingException {
        Booking booking = new Booking();
        booking.setGuestName(bookingDto.getGuestName());
        booking.setEmail(bookingDto.getEmail());
        booking.setPhone(bookingDto.getMobileNumber());
        booking.setCheckInDate(bookingDto.getCheckInDate());
        booking.setCheckOutDate(bookingDto.getCheckOutDate());
        booking.setTotalPrice(bookingDto.getTotalPrice());
        Room room =roomRepo.findByRoomId(bookingDto.getRoomId()).orElseThrow(() -> new IllegalArgumentException("Room not found"));
        room.setAvailability(false);
        booking.setRoom(room);
        booking.setPaymentStatus("Pay At Hotel");
        User user =userRepo.findById(bookingDto.getUserId()).orElseThrow(() -> new IllegalArgumentException("User not found"));
        booking.setUser(user);

        bookingRepo.save(booking);
        ByteArrayOutputStream outputStream=generatePdfConfirmation(booking);
        sendEmailWithAttachment(bookingDto.getEmail(), "Thank You for Booking Room With HOme.com", "Please find your booking confirmation attached.", outputStream.toByteArray());

    }

    private void sendEmailWithAttachment(String toEmail, String subject, String text, byte[] attachment) throws MessagingException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setTo(toEmail);
        helper.setSubject(subject);
        helper.setText(text);
        helper.addAttachment("BookingConfirmation.pdf", new ByteArrayResource(attachment));
        javaMailSender.send(message);
    }
    private ByteArrayOutputStream generatePdfConfirmation(Booking booking) throws IOException {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                // Set font styles
                contentStream.setFont(PDType1Font.HELVETICA_BOLD, 16);

                // Title
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 750);
                contentStream.showText("Booking Confirmation");
                contentStream.endText();

                // Booking details
                contentStream.setFont(PDType1Font.HELVETICA, 12);

                // Move down to display the first detail
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 700);
                contentStream.showText("Guest Name: " + booking.getGuestName());
                contentStream.endText();

                // Move down to display the second detail
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 680); // Adjust the Y-coordinate as needed
                contentStream.showText("Email: " + booking.getEmail());
                contentStream.endText();

                // Move down to display the third detail
                contentStream.beginText();
                contentStream.newLineAtOffset(50, 660); // Adjust the Y-coordinate as needed
                contentStream.showText("Phone: " + booking.getPhone());
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(50, 640); // Adjust the Y-coordinate as needed
                contentStream.showText("CheckInDate: " + booking.getCheckInDate());
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(50, 620); // Adjust the Y-coordinate as needed
                contentStream.showText("CheckOutDate: " + booking.getCheckOutDate());
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(50, 600); // Adjust the Y-coordinate as needed
                contentStream.showText("Room Number: " + booking.getRoom().getRoomNumber());
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(50, 580); // Adjust the Y-coordinate as needed
                contentStream.showText("Room type: " + booking.getRoom().getRoomType());
                contentStream.endText();

                contentStream.beginText();
                contentStream.newLineAtOffset(50, 540); // Adjust the Y-coordinate as needed
                contentStream.showText("Room Price: " + booking.getRoom().getPricePerNight());
                contentStream.endText();

                contentStream.addRect(40, 530, 250, 240); // Adjust the rectangle dimensions and position as needed
                contentStream.stroke();
            }


            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            document.save(outputStream);
            return outputStream;
        }
    }

}
