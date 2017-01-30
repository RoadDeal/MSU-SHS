/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ised.service.implementation;

import ised.DAO.implementation.SectionDAOImpl;
import ised.DAO.interfaces.SectionDAO;
import ised.model.SchoolYear;
import ised.model.Section;
import ised.service.interfaces.SectionService;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public class SectionServiceImpl implements SectionService {

    SectionDAO dao;

    public SectionServiceImpl() {
        dao = new SectionDAOImpl();
    }

    public void addSection(String sectionName) throws ExceptionHandler {
        dao.addSection(sectionName);
    }

    public void editSection(String sectionName, int sectionID) throws ExceptionHandler {
        dao.editSection(sectionName, sectionID);
    }

    public void deleteSection(int sectionID) throws ExceptionHandler {
        dao.deleteSection(sectionID);
    }

    public void changeSectionStatus(int sectionID, String status) throws ExceptionHandler {
        dao.changeSectionStatus(sectionID, status);
    }

    public List<Section> getSectionList() throws ExceptionHandler {
        return dao.getSectionList();
    }

    public List<Section> getSectionList(String status) throws ExceptionHandler {
        return dao.getSectionList(status);
    }

    public Section getSection(int sectionID) throws ExceptionHandler {
        return dao.getSection(sectionID);
    }

    public boolean isSectionExist(String name) throws ExceptionHandler {
        return dao.isSectionExist(name);
    }
}
