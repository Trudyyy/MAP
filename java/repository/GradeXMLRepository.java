package repository;

import domain.Entity;
import domain.Grade;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import java.time.LocalDateTime;

public class GradeXMLRepository extends AbstractXMLRepository {
    public GradeXMLRepository(String fileName) {
        super(fileName);
    }

    @Override
    public Entity createEntityFromElement(Element element) {
        String[] id = element.getAttribute("id").split(" ");
        String idStudent = id[0];
        String idHomework = id[1];
        int value = Integer.parseInt(element.getElementsByTagName("value")
                .item(0)
                .getTextContent());

        LocalDateTime localDateTime = LocalDateTime.parse(
                element.getElementsByTagName("localDateTime")
                        .item(0)
                        .getTextContent()
        );
        String teacher = element.getElementsByTagName("teacher")
                .item(0)
                .getTextContent();

        Grade grade = new Grade(idStudent, idHomework, value, localDateTime, teacher);
        return grade;
    }

    @Override
    public Element createElementFromEntity(Document document, Entity entity) {
        Grade grade = (Grade) entity;

        Element element = document.createElement("grade");
        element.setAttribute("id", grade.getId());

        Element value = document.createElement("value");
        value.setTextContent(String.valueOf(grade.getValue()));
        element.appendChild(value);

        Element localDateTime = document.createElement("localDateTime");
        localDateTime.setTextContent(grade.getLocalDateTime().toString());
        element.appendChild(localDateTime);

        Element teacher = document.createElement("teacher");
        teacher.setTextContent(grade.getTeacherId());
        element.appendChild(teacher);

        return element;
    }
}
