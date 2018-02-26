package com.vistula.schedule;


import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.WebResponse;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlOption;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlSelect;
import com.pdfcrowd.Pdfcrowd;

public class Schedule {

    public static String getSchedule() throws Exception{

        String schedule = "";

        try (final WebClient webClient = new WebClient()){

            final HtmlPage startingPage = webClient.getPage("http://plan.vistula.edu.pl/Scientia/SWS/PROD1718/default.aspx");

            final HtmlElement studentsGroup = (HtmlElement) startingPage.getElementById("LinkBtn_StudentSets");
            final HtmlPage formPage = studentsGroup.click();

            final HtmlSelect selectGroup = (HtmlSelect) formPage.getElementById("dlObject");
            final HtmlOption optionGroup = selectGroup.getOptionByValue("4852");
            selectGroup.setSelectedAttribute(optionGroup, true);

            final HtmlSelect selectTime = (HtmlSelect) formPage.getElementById("lbWeeks");
            final HtmlOption optionTime = selectTime.getOptionByValue("22-52");
            selectTime.setSelectedAttribute(optionTime, true);


            final HtmlElement sumbitButton = (HtmlElement) formPage.getElementById("bGetTimetable");
            final HtmlPage schedulePage = sumbitButton.click();

            final WebResponse response = schedulePage.getWebResponse();
            schedule = schedulePage.getTitleText();
        }


        return schedule;
    }

    public static byte[] convertToImg(String html){

        byte[] img = null;

        try {
            Pdfcrowd.HtmlToImageClient client = new Pdfcrowd.HtmlToImageClient("dislike", "4bd0f148d0ef83a69dca46c25450a7ef");
            client.setOutputFormat("png");
            img = client.convertString(html);
        }
        catch(Pdfcrowd.Error e) {
            System.err.println("Pdfcrowd Error: " + e);
        }
        return img;
    }
}
