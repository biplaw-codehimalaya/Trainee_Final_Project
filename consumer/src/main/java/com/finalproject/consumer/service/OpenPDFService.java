package com.finalproject.consumer.service;

import com.finalproject.consumer.dto.ReceivedData;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

@Service
public class OpenPDFService {

    Logger LOGGER = LoggerFactory.getLogger(OpenPDFService.class);

    //Creates the pdf report of the data
    public ByteArrayOutputStream createPDF(ReceivedData receivedData){
        LOGGER.info("CREATE PDF CALLED FOR " + receivedData);

        //Creating a new outputstream
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //Opening the document
        Document document = new Document();

        try{
            //Get theinstance of writeer
            PdfWriter.getInstance(document, byteArrayOutputStream);

            //Open the document
            document.open();

            //Setting the product name
            Font titleFont= FontFactory.getFont(FontFactory.TIMES_BOLDITALIC, 20);
            Paragraph titleParagraph = new Paragraph(receivedData.getName(), titleFont);
            //Setting the alignment
            titleParagraph.setAlignment(Element.ALIGN_LEFT);
            document.add(titleParagraph);

            //Set the products Description
            Font bodyFont = FontFactory.getFont(FontFactory.TIMES, 14);
            Paragraph descParagraph = new Paragraph("Description: " + receivedData.getDescription(), bodyFont);
            document.add(descParagraph);

            //Set the products Category
            Paragraph categoryParagraph = new Paragraph("Category: " + receivedData.getCategoryName(), bodyFont);
            document.add(categoryParagraph);

            //Set the products Category
            Paragraph priceParagraph = new Paragraph("Price: " + receivedData.getPrice(), bodyFont);
            document.add(priceParagraph);

            //close the document
            document.close();

            return byteArrayOutputStream;

        }catch (DocumentException ex){
            LOGGER.error(ex.toString());
        }
        return null;
    }
}
