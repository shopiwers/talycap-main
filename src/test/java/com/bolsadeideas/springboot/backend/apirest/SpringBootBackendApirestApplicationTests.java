//package com.bolsadeideas.springboot.backend.apirest;
//
//
//import static org.mockito.Mockito.*;
//
//
//
//import org.junit.jupiter.api.BeforeEach;
//import org.junit.jupiter.api.Test;
//import org.junit.jupiter.api.extension.ExtendWith;
//import org.mockito.InjectMocks;
//import org.mockito.Mock;
//import org.mockito.MockitoAnnotations;
//import org.mockito.junit.jupiter.MockitoExtension;
//
//import org.springframework.validation.BindingResult;
//
//import com.bolsadeideas.springboot.backend.apirest.controllers.ClienteRestController;
//
//import com.bolsadeideas.springboot.backend.apirest.models.services.IClienteService;
//
//@ExtendWith(MockitoExtension.class)
//class SpringBootBackendApirestApplicationTests {
//
//    @Mock
//    private IClienteService clienteService;
//
//    @InjectMocks
//    private ClienteRestController clienteRestController;
//
//  
//    private BindingResult bindingResult;
//
//    @BeforeEach
//    void setUp() {
//        MockitoAnnotations.initMocks(this);
//       
//
//        bindingResult = mock(BindingResult.class);
//    }
//
//    @Test
//    void testCreate() {
//        when(bindingResult.hasErrors()).thenReturn(false); // Simula que no hay errores de validación
//       
//
//       
//
//    }
//
//    @Test
//    void testUpdate() {
//    
//    }
//}
