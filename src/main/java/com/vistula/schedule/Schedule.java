package com.vistula.schedule;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.pdfcrowd.Pdfcrowd;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by User on 27-Feb-18.
 */
public class Schedule {

    public static String getSchedule(String group) throws Exception{

        String schedule = "";

        try (final WebClient webClient = new WebClient()){

            final HtmlPage startingPage = webClient.getPage("http://plan.vistula.edu.pl/Scientia/SWS/PROD1718/default.aspx");

            final HtmlElement studentsGroup = (HtmlElement) startingPage.getElementById("LinkBtn_StudentSets");
            final HtmlPage formPage = studentsGroup.click();

            final HtmlSelect selectGroup = (HtmlSelect) formPage.getElementById("dlObject");
            if(group.equals("group 2")) {
                final HtmlOption optionGroup = selectGroup.getOptionByValue("4852");
                selectGroup.setSelectedAttribute(optionGroup, true);
            }
            else if(group.equals("group 1")) {
                final HtmlOption optionGroup = selectGroup.getOptionByValue("4851");
                selectGroup.setSelectedAttribute(optionGroup, true);
            }
            final HtmlSelect selectTime = (HtmlSelect) formPage.getElementById("lbWeeks");
            final HtmlOption optionTime = selectTime.getOptionByValue("22-52");
            selectTime.setSelectedAttribute(optionTime, true);


            final HtmlElement sumbitButton = (HtmlElement) formPage.getElementById("bGetTimetable");
            final HtmlPage schedulePage = sumbitButton.click();

            final WebResponse response = schedulePage.getWebResponse();
            schedule = response.getContentAsString();
        }

        return schedule;
    }

    public static java.io.File convertToImg(String html){

        File file = new File("Schedule.png");

        try {
            Pdfcrowd.HtmlToImageClient client = new Pdfcrowd.HtmlToImageClient("dislike", "4bd0f148d0ef83a69dca46c25450a7ef");
            client.setOutputFormat("png");
            FileOutputStream output_stream = new FileOutputStream(file);
            client.convertStringToStream(html, output_stream);
            output_stream.close();
        }
        catch(Pdfcrowd.Error e) {
            System.err.println("Pdfcrowd Error: " + e);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return file;
    }
}
