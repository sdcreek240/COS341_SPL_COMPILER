package spl.testing.generator;

import java.io.IOException;
import java.util.stream.Stream;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import spl.testing.support.GoldenFixtureAssert;
import spl.testing.support.GoldenFixtures;

class BooleanFixtureTest {

    @ParameterizedTest(name = "{0}")
    @MethodSource("fixtures")
    void golden(GoldenFixtures.Fixture fx) throws IOException {
        GoldenFixtureAssert.assertMatchesGolden(fx);
    }//END_golden

    static Stream<GoldenFixtures.Fixture> fixtures() throws IOException {
        return GoldenFixtures.inCategory("booleans");
    }//END_fixtures

}//END_BooleanFixtureTest