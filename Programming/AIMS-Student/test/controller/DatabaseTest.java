package controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import entity.media.Media;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.sql.SQLException;

/**
 * description
 *
 * @author Nguyen Thai An
 * <p>
 * creted at: 01/12/2020
 * <p>
 * project name: AIMS-Student
 * <p>
 * teacher's name: Dr. Nguyen Thi Thu Trang
 * <p>
 * class name: TT.CNTT ICT 02 - K62
 */
public class DatabaseTest {
    private Media media;

    @BeforeEach
    void setUp() throws SQLException {
        media = new Media();
    }

    @ParameterizedTest
    @CsvSource({
            "38, true",
            "39, false",
            "46, true",
            "51, false"
    })
    public void getMediaByIdTest(int id, boolean expected) throws SQLException {
        Media m = media.getMediaById(id);
        assertEquals(m.isRushSupported(), expected);
    }
}
