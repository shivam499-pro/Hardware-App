package com.manish.hardware.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.manish.hardware.dto.AuthDtos;
import com.manish.hardware.model.*;
import com.manish.hardware.repository.*;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * End-to-End Integration Tests for Manish Hardware Backend
 * Tests the complete flow from API endpoints to database
 */
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EndToEndIntegrationTest {

        @Autowired
        private MockMvc mockMvc;

        @Autowired
        private ObjectMapper objectMapper;

        @Autowired
        private AdminUserRepository adminUserRepository;

        @Autowired
        private CategoryRepository categoryRepository;

        @Autowired
        private ProductRepository productRepository;

        @Autowired
        private BannerRepository bannerRepository;

        @Autowired
        private QuoteRequestRepository quoteRequestRepository;

        @Autowired
        private SupportedLanguageRepository supportedLanguageRepository;

        @Autowired
        private AppConfigRepository appConfigRepository;

        @Autowired
        private MessageTemplateRepository messageTemplateRepository;

        @Autowired
        private PasswordEncoder passwordEncoder;

        private static String jwtToken;
        private static Long categoryId;
        private static Long productId;
        private static Long bannerId;
        private static Long quoteId;

        @BeforeEach
        void setUp() {
                // Clean up repositories for isolated tests
                // Verify all repositories are properly injected
                assert adminUserRepository != null;
                assert categoryRepository != null;
                assert productRepository != null;
                assert bannerRepository != null;
                assert quoteRequestRepository != null;
                assert supportedLanguageRepository != null;
                assert appConfigRepository != null;
                assert messageTemplateRepository != null;
                assert passwordEncoder != null;
        }

        @Test
        @Order(1)
        void testHealthCheck() throws Exception {
                mockMvc.perform(get("/api/v1/categories"))
                                .andExpect(status().isOk());
        }

        @Test
        @Order(1)
        void testRepositoryInjection() {
                // Test that all repositories are properly injected and can perform basic
                // operations
                long userCount = adminUserRepository.count();
                long categoryCount = categoryRepository.count();
                long productCount = productRepository.count();
                long bannerCount = bannerRepository.count();
                long quoteCount = quoteRequestRepository.count();
                long languageCount = supportedLanguageRepository.count();
                long configCount = appConfigRepository.count();
                long templateCount = messageTemplateRepository.count();

                // Verify password encoder works
                String encoded = passwordEncoder.encode("testPassword");
                assert passwordEncoder.matches("testPassword", encoded);

                System.out.println("Repository counts - Users: " + userCount +
                                ", Categories: " + categoryCount +
                                ", Products: " + productCount +
                                ", Banners: " + bannerCount +
                                ", Quotes: " + quoteCount +
                                ", Languages: " + languageCount +
                                ", Configs: " + configCount +
                                ", Templates: " + templateCount);
        }

        @Test
        @Order(2)
        void testRegisterAndLogin() throws Exception {
                // Register a new admin user
                AuthDtos.RegisterRequest registerRequest = new AuthDtos.RegisterRequest();
                registerRequest.setUsername("testadmin");
                registerRequest.setPassword("password123");
                registerRequest.setEmail("testadmin@test.com");
                registerRequest.setFullName("Test Admin");

                mockMvc.perform(post("/api/v1/auth/register")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(registerRequest)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.message").value("User registered successfully"));

                // Login with the registered user
                AuthDtos.LoginRequest loginRequest = new AuthDtos.LoginRequest();
                loginRequest.setUsername("testadmin");
                loginRequest.setPassword("password123");

                MvcResult result = mockMvc.perform(post("/api/v1/auth/login")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(loginRequest)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.accessToken").exists())
                                .andReturn();

                // Extract JWT token for subsequent tests
                String response = result.getResponse().getContentAsString();
                jwtToken = objectMapper.readTree(response).get("accessToken").asText();
        }

        @Test
        @Order(3)
        void testCategoryCRUD() throws Exception {
                // Create Category
                Category category = new Category();
                category.setName("Test Category");
                category.setDescription("Test Description");
                category.setSortOrder(1);
                category.setIsActive(true);

                MvcResult createResult = mockMvc.perform(post("/api/v1/categories")
                                .header("Authorization", "Bearer " + jwtToken)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(category)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").exists())
                                .andReturn();

                categoryId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

                // Read Category
                mockMvc.perform(get("/api/v1/categories/" + categoryId))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Test Category"));

                // Update Category
                category.setName("Updated Category");
                mockMvc.perform(put("/api/v1/categories/" + categoryId)
                                .header("Authorization", "Bearer " + jwtToken)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(category)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Updated Category"));

                // Get All Categories
                mockMvc.perform(get("/api/v1/categories"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());
        }

        @Test
        @Order(4)
        void testProductCRUD() throws Exception {
                // Create Product
                Product product = new Product();
                product.setCategoryId(categoryId);
                product.setBrand("Test Brand");
                product.setIsActive(true);

                MvcResult createResult = mockMvc.perform(post("/api/v1/products")
                                .header("Authorization", "Bearer " + jwtToken)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(product)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").exists())
                                .andReturn();

                productId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

                // Read Product
                mockMvc.perform(get("/api/v1/products/" + productId))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.brand").value("Test Brand"));

                // Update Product
                product.setBrand("Updated Brand");
                mockMvc.perform(put("/api/v1/products/" + productId)
                                .header("Authorization", "Bearer " + jwtToken)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(product)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.brand").value("Updated Brand"));

                // Get Products by Category
                mockMvc.perform(get("/api/v1/products/category/" + categoryId))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").isArray());
        }

        @Test
        @Order(5)
        void testProductTranslation() throws Exception {
                // Add Translation
                ProductTranslation translation = new ProductTranslation();
                translation.setLanguageCode("en");
                translation.setName("Test Product");
                translation.setDescription("Test Product Description");

                mockMvc.perform(post("/api/v1/products/" + productId + "/translations")
                                .header("Authorization", "Bearer " + jwtToken)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(translation)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Test Product"));

                // Get Translations
                mockMvc.perform(get("/api/v1/products/" + productId + "/translations"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());

                // Check Translation Exists
                mockMvc.perform(get("/api/v1/products/" + productId + "/translations/en/exists"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("true"));
        }

        @Test
        @Order(6)
        void testBannerCRUD() throws Exception {
                // Create Banner
                Banner banner = new Banner();
                banner.setTitle("Test Banner");
                banner.setImageUrl("http://example.com/image.jpg");
                banner.setSortOrder(1);
                banner.setIsActive(true);

                MvcResult createResult = mockMvc.perform(post("/api/v1/banners")
                                .header("Authorization", "Bearer " + jwtToken)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(banner)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").exists())
                                .andReturn();

                bannerId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

                // Read Banner
                mockMvc.perform(get("/api/v1/banners/" + bannerId))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.title").value("Test Banner"));

                // Get Active Banners
                mockMvc.perform(get("/api/v1/banners"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());

                // Update Sort Order
                mockMvc.perform(put("/api/v1/banners/" + bannerId + "/sort?sortOrder=5")
                                .header("Authorization", "Bearer " + jwtToken)
                                .with(csrf()))
                                .andExpect(status().isOk());
        }

        @Test
        @Order(7)
        void testQuoteRequestFlow() throws Exception {
                // Submit Quote Request (Public)
                QuoteRequest quote = new QuoteRequest();
                quote.setName("Test Customer");
                quote.setPhone("9876543210");
                quote.setProductId(productId);
                quote.setQuantity("10 bags");
                quote.setLocation("Kathmandu");

                MvcResult createResult = mockMvc.perform(post("/api/v1/quotes")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(quote)))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.id").exists())
                                .andReturn();

                quoteId = objectMapper.readTree(createResult.getResponse().getContentAsString()).get("id").asLong();

                // Get Quote by ID (Admin)
                mockMvc.perform(get("/api/v1/quotes/" + quoteId)
                                .header("Authorization", "Bearer " + jwtToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.name").value("Test Customer"));

                // Update Quote Status
                mockMvc.perform(patch("/api/v1/quotes/" + quoteId + "/status?status=CONTACTED")
                                .header("Authorization", "Bearer " + jwtToken)
                                .with(csrf()))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.status").value("CONTACTED"));

                // Get Quote Statistics
                mockMvc.perform(get("/api/v1/quotes/statistics")
                                .header("Authorization", "Bearer " + jwtToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.total").exists());
        }

        @Test
        @Order(8)
        void testSupportedLanguageOperations() throws Exception {
                // Get Active Languages
                mockMvc.perform(get("/api/v1/languages"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());

                // Get Default Language
                mockMvc.perform(get("/api/v1/languages/default"))
                                .andExpect(status().isOk());

                // Check Language Support
                mockMvc.perform(get("/api/v1/languages/en/supported"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("true"));
        }

        @Test
        @Order(9)
        void testAppConfigOperations() throws Exception {
                // Get Business Config
                mockMvc.perform(get("/api/v1/config/business"))
                                .andExpect(status().isOk());

                // Get All Configs
                mockMvc.perform(get("/api/v1/config/admin/all")
                                .header("Authorization", "Bearer " + jwtToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());

                // Save Config
                mockMvc.perform(put("/api/v1/config/admin/test_key")
                                .header("Authorization", "Bearer " + jwtToken)
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"value\":\"test_value\"}"))
                                .andExpect(status().isOk());

                // Get Config Value
                mockMvc.perform(get("/api/v1/config/test_key/value"))
                                .andExpect(status().isOk())
                                .andExpect(content().string("test_value"));
        }

        @Test
        @Order(10)
        void testMessageTemplateOperations() throws Exception {
                // Get All Templates
                mockMvc.perform(get("/api/v1/templates"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());

                // Get Template Types
                mockMvc.perform(get("/api/v1/templates/types"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$").isArray());

                // Render WhatsApp Quote Template
                mockMvc.perform(post("/api/v1/templates/render/whatsapp-quote?languageCode=en")
                                .with(csrf())
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{\"product\":\"Cement\",\"quantity\":\"10 bags\",\"name\":\"Test User\",\"location\":\"Kathmandu\"}"))
                                .andExpect(status().isOk());
        }

        @Test
        @Order(11)
        void testAdminDashboard() throws Exception {
                // Get Dashboard Stats
                mockMvc.perform(get("/api/v1/admin/dashboard")
                                .header("Authorization", "Bearer " + jwtToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.totalCategories").exists())
                                .andExpect(jsonPath("$.quoteStats").exists());

                // Get Stats
                mockMvc.perform(get("/api/v1/admin/stats")
                                .header("Authorization", "Bearer " + jwtToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.categories").exists())
                                .andExpect(jsonPath("$.totalQuotes").exists());
        }

        @Test
        @Order(12)
        void testSearchOperations() throws Exception {
                // Search Products
                mockMvc.perform(get("/api/v1/products/search?q=Test"))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").isArray());

                // Search Quotes
                mockMvc.perform(get("/api/v1/quotes/search?q=Test")
                                .header("Authorization", "Bearer " + jwtToken))
                                .andExpect(status().isOk())
                                .andExpect(jsonPath("$.content").isArray());
        }

        @Test
        @Order(100)
        void cleanup() throws Exception {
                // Delete test data in reverse order
                if (quoteId != null) {
                        mockMvc.perform(delete("/api/v1/quotes/" + quoteId)
                                        .header("Authorization", "Bearer " + jwtToken)
                                        .with(csrf()))
                                        .andExpect(status().isOk());
                }

                if (bannerId != null) {
                        mockMvc.perform(delete("/api/v1/banners/" + bannerId)
                                        .header("Authorization", "Bearer " + jwtToken)
                                        .with(csrf()))
                                        .andExpect(status().isOk());
                }

                if (productId != null) {
                        mockMvc.perform(delete("/api/v1/products/" + productId)
                                        .header("Authorization", "Bearer " + jwtToken)
                                        .with(csrf()))
                                        .andExpect(status().isOk());
                }

                if (categoryId != null) {
                        mockMvc.perform(delete("/api/v1/categories/" + categoryId)
                                        .header("Authorization", "Bearer " + jwtToken)
                                        .with(csrf()))
                                        .andExpect(status().isOk());
                }
        }
}
