package com.examly.springapp;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import java.io.File;
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest(classes = SpringappApplication.class)
@AutoConfigureMockMvc
class SpringappGameTests {

    @Autowired
    private MockMvc mockMvc;

    // ---------- Core API Tests ----------

    @Order(1)
    @Test
    void AddGameReturns200() throws Exception {
        String gameData = """
                {
                    "gameName": "Clash of Clans",
                    "developer": "Supercell",
                    "genre": "Strategy",
                    "rating": 4.5,
                    "downloads": 1000000
                }
                """;

        mockMvc.perform(MockMvcRequestBuilders.post("/api/games/addGame")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameData)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.gameName").value("Clash of Clans"))
                .andReturn();
    }

    @Order(2)
    @Test
    void GetAllGamesReturnsArray() throws Exception {
        mockMvc.perform(get("/api/games/allGames")
                        .with(jwt())
                        .accept(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @Order(3)
    @Test
    void GetGamesByGenreReturns200() throws Exception {
        mockMvc.perform(get("/api/games/byGenre")
                        .with(jwt())
                        .param("genre", "Strategy")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].gameName").exists())
                .andReturn();
    }

    @Order(4)
    @Test
    void GetGamesSortedByRatingReturns200() throws Exception {
        mockMvc.perform(get("/api/games/sortedByRating")
                        .with(jwt())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andReturn();
    }

    @Order(5)
    @Test
    void DeleteGameReturns204() throws Exception {
        // First add one to delete
        String gameData = """
                {
                    "gameName": "ToDelete",
                    "developer": "TestDev",
                    "genre": "Action",
                    "rating": 3.0,
                    "downloads": 500
                }
                """;

        String response = mockMvc.perform(MockMvcRequestBuilders.post("/api/games/addGame")
                        .with(jwt())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(gameData)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        assertTrue(response.contains("ToDelete"));

        // Try deleting id = 1 (or use repository in real case to extract ID)
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/games/1").with(jwt()))
                .andExpect(status().isNoContent());
    }

    // ---------- Project Structure Tests ----------

    @Test
    void ControllerDirectoryExists() {
        String directoryPath = "src/main/java/com/examly/springapp/controller";
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    @Test
    void GameControllerFileExists() {
        String filePath = "src/main/java/com/examly/springapp/controller/GameController.java";
        File file = new File(filePath);
        assertTrue(file.exists() && file.isFile());
    }

    @Test
    void ModelDirectoryExists() {
        String directoryPath = "src/main/java/com/examly/springapp/model";
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    @Test
    void GameModelFileExists() {
        String filePath = "src/main/java/com/examly/springapp/model/Game.java";
        File file = new File(filePath);
        assertTrue(file.exists() && file.isFile());
    }

    @Test
    void RepositoryDirectoryExists() {
        String directoryPath = "src/main/java/com/examly/springapp/repository";
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    @Test
    void ServiceDirectoryExists() {
        String directoryPath = "src/main/java/com/examly/springapp/service";
        File directory = new File(directoryPath);
        assertTrue(directory.exists() && directory.isDirectory());
    }

    @Test
    void GameServiceClassExists() {
        checkClassExists("com.examly.springapp.service.GameService");
    }

    @Test
    void GameModelClassExists() {
        checkClassExists("com.examly.springapp.model.Game");
    }

    @Test
    void GameModelHasGameNameField() {
        checkFieldExists("com.examly.springapp.model.Game", "gameName");
    }

    @Test
    void GameModelHasDeveloperField() {
        checkFieldExists("com.examly.springapp.model.Game", "developer");
    }

    @Test
    void GameModelHasGenreField() {
        checkFieldExists("com.examly.springapp.model.Game", "genre");
    }

    @Test
    void GameModelHasRatingField() {
        checkFieldExists("com.examly.springapp.model.Game", "rating");
    }

    @Test
    void GameModelHasDownloadsField() {
        checkFieldExists("com.examly.springapp.model.Game", "downloads");
    }

    @Test
    void GameRepoExtendsJpaRepository() {
        checkClassImplementsInterface("com.examly.springapp.repository.GameRepository",
                "org.springframework.data.jpa.repository.JpaRepository");
    }

    // ---------- Helpers ----------

    private void checkClassExists(String className) {
        try {
            Class.forName(className);
        } catch (ClassNotFoundException e) {
            fail("Class " + className + " does not exist.");
        }
    }

    private void checkFieldExists(String className, String fieldName) {
        try {
            Class<?> clazz = Class.forName(className);
            clazz.getDeclaredField(fieldName);
        } catch (ClassNotFoundException | NoSuchFieldException e) {
            fail("Field " + fieldName + " in class " + className + " does not exist.");
        }
    }

    private void checkClassImplementsInterface(String className, String interfaceName) {
        try {
            Class<?> clazz = Class.forName(className);
            Class<?> interfaceClazz = Class.forName(interfaceName);
            assertTrue(interfaceClazz.isAssignableFrom(clazz));
        } catch (ClassNotFoundException e) {
            fail("Class " + className + " or interface " + interfaceName + " does not exist.");
        }
    }
}
