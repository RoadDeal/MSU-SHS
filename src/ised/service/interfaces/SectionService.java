/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package ised.service.interfaces;

import ised.model.Section;
import ised.tools.ExceptionHandler;
import java.util.List;

/**
 *
 * @author ABDUL
 */
public interface SectionService{

    void addSection(String sectionName) throws ExceptionHandler;

    void editSection(String sectionName, int sectionID) throws ExceptionHandler;

    void deleteSection(int sectionID) throws ExceptionHandler;

    void changeSectionStatus(int sectionID, String status) throws ExceptionHandler;

    Section getSection(int sectionID) throws ExceptionHandler;

    List<Section> getSectionList() throws ExceptionHandler;

    List<Section> getSectionList(String status) throws ExceptionHandler;

    boolean isSectionExist(String sectionName) throws ExceptionHandler;
}
