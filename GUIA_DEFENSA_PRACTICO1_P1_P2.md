# Guia de estudio oral - Practico 1 (Partes 1 y 2)

> Enfoque: preguntas probables del licenciado + respuesta desarrollada + donde mostrar codigo.
> Alcance: solo **1) Lista de recetas** y **2) Crear receta**.

## 0) Mapa rapido del flujo (para abrir la defensa)

1. `MainActivity` levanta la app y crea `RecetaViewModel`.
2. `AppNavGraph` define rutas y pantallas.
3. `ListaRecetasScreen` muestra, busca y filtra recetas.
4. Al tocar "Agregar receta", se navega a `CrearRecetaScreen`.
5. En `CrearRecetaScreen` se arma la receta y se valida.
6. Se llama `onSaveRecipe(...)` y `AppNavGraph` ejecuta `recetaViewModel.agregarReceta(...)`.
7. Cambia el estado `recetas` y Compose recompone la UI.
8. Al volver, la receta nueva ya aparece en la lista.

## 1) Archivos clave para mostrar en vivo

- `app/src/main/java/com/example/planificador_de_comidas/MainActivity.kt`
- `app/src/main/java/com/example/planificador_de_comidas/navigation/AppNavGraph.kt`
- `app/src/main/java/com/example/planificador_de_comidas/navigation/AppRoutes.kt`
- `app/src/main/java/com/example/planificador_de_comidas/viewmodel/RecetaViewModel.kt`
- `app/src/main/java/com/example/planificador_de_comidas/screens/ListaRecetasScreen.kt`
- `app/src/main/java/com/example/planificador_de_comidas/screens/CrearRecetaScreen.kt`
- `app/src/main/java/com/example/planificador_de_comidas/components/FilaEntradaIngrediente.kt`
- `app/src/main/java/com/example/planificador_de_comidas/components/TarjetaReceta.kt`
- `app/src/main/java/com/example/planificador_de_comidas/model/Receta.kt`
- `app/src/main/java/com/example/planificador_de_comidas/model/Ingrediente.kt`

## 2) Banco de preguntas probables (flujo de dificultad)

## Nivel A - Fundamentos (inicio de oral)

### 1) Donde inicia la app y que hace primero?
- Respuesta: La app inicia en `MainActivity.onCreate`. Ahi se llama `setContent { ... }`, que es donde Compose dibuja toda la interfaz. Dentro de ese bloque se aplica el tema con `PlanificadordecomidasTheme` y se crea `RecetaViewModel` usando `viewModel()`. Esto permite tener un estado central para recetas que sobreviva recomposiciones y sea compartido por las pantallas.
- Mostrar: `MainActivity.kt` (`onCreate`, `setContent`, `viewModel`, `AppNavGraph`).

### 2) Por que usan `ViewModel` y no variables sueltas en pantalla?
- Respuesta: Porque el `ViewModel` concentra la logica y el estado de negocio (`recetas`, filtros y operaciones). Si todo estuviera en la pantalla, el codigo quedaria acoplado a UI y mas dificil de mantener. Ademas, `ViewModel` es el lugar correcto para estado que se comparte entre pantallas (lista y crear receta) y evita perderlo en recomposiciones.
- Mostrar: `RecetaViewModel.kt` y como se inyecta en `MainActivity.kt`.

### 3) Como se conectan las pantallas?
- Respuesta: Se usa Navigation Compose en `AppNavGraph`. Con `NavHost` se define destino inicial y cada `composable(...)` representa una pantalla. Eso crea un flujo claro: lista -> crear receta -> volver.
- Mostrar: `AppNavGraph.kt` (`NavHost`, `composable(AppRoutes.ListaRecetas)`, `composable(AppRoutes.CrearReceta)`).

### 4) Donde guardan los nombres de rutas y por que?
- Respuesta: En `AppRoutes.kt`, como constantes. Esto evita hardcodear strings repetidos en varios archivos y reduce errores por typo al navegar.
- Mostrar: `AppRoutes.kt`.

### 5) Cual es el destino inicial de la app?
- Respuesta: `AppRoutes.ListaRecetas`, definido en `startDestination` del `NavHost`.
- Mostrar: `AppNavGraph.kt` linea de `startDestination = AppRoutes.ListaRecetas`.

### 6) Que datos modelan una receta?
- Respuesta: Una `Receta` tiene `id`, `nombre` e `ingredientes`. Cada `Ingrediente` tiene `nombre`, `cantidad` y `unidad`. Se usan `data class` para representar datos de forma simple y legible.
- Mostrar: `model/Receta.kt` y `model/Ingrediente.kt`.

## Nivel B - Parte 1: Lista de recetas

### 7) Como se dibuja la pantalla de lista?
- Respuesta: En `ListaRecetasScreen` se arma un `Scaffold` y dentro un `Column` con campos de busqueda/filtro, botones y una lista `LazyColumn`. Si no hay resultados, se muestra mensaje vacio; si hay, se renderiza cada receta con `TarjetaReceta`.
- Mostrar: `ListaRecetasScreen.kt`.

### 8) Como funciona la busqueda por nombre?
- Respuesta: El campo `OutlinedTextField` de busqueda actualiza `viewModel.busquedaNombre` mediante `actualizarBusquedaNombre`. Luego la propiedad calculada `recetasFiltradas` usa `contains(..., ignoreCase = true)` sobre `receta.nombre` para decidir que mostrar.
- Mostrar: `ListaRecetasScreen.kt` (`onValueChange = viewModel::actualizarBusquedaNombre`) y `RecetaViewModel.kt` (`recetasFiltradas`).

### 9) Como funciona el filtro por ingrediente?
- Respuesta: Similar a la busqueda. El segundo `OutlinedTextField` actualiza `filtroIngrediente`. En `recetasFiltradas`, si el filtro esta vacio deja pasar todo; si no, revisa con `any` si algun ingrediente coincide por nombre, ignorando mayusculas/minusculas.
- Mostrar: `ListaRecetasScreen.kt` y `RecetaViewModel.kt`.

### 10) Que pasa si no hay resultados al filtrar?
- Respuesta: Se evalua `if (viewModel.recetasFiltradas.isEmpty())` y se muestra el texto "No hay recetas para mostrar". Esto mejora UX porque el usuario entiende que no es error, sino ausencia de coincidencias.
- Mostrar: `ListaRecetasScreen.kt` bloque `if/else` de lista.

### 11) Por que usan `LazyColumn` y no `Column` para la lista?
- Respuesta: `LazyColumn` renderiza eficientemente items visibles, ideal para listas que pueden crecer. Aunque hoy el volumen sea pequeno, es buena practica base para escalabilidad.
- Mostrar: `ListaRecetasScreen.kt` (`LazyColumn`, `items(...)`).

### 12) Donde se define como se ve cada receta?
- Respuesta: En el componente reutilizable `TarjetaReceta`. Se recibe una `Receta` y se muestran nombre e ingredientes dentro de una `Card`.
- Mostrar: `components/TarjetaReceta.kt`.

### 13) Que ventaja tiene separar `TarjetaReceta` en otro archivo?
- Respuesta: Reutilizacion y claridad. La pantalla de lista queda enfocada en layout y flujo, y el detalle visual de cada item queda encapsulado en su propio componente.
- Mostrar: `ListaRecetasScreen.kt` + `TarjetaReceta.kt`.

### 14) Como esta cargada la lista inicial?
- Respuesta: `RecetaViewModel` inicia `recetas` con una receta de ejemplo (`Ensalada simple`) para no tener la app vacia al abrirla y poder probar busqueda/filtro de inmediato.
- Mostrar: `RecetaViewModel.kt` bloque inicial de `recetas`.

### 15) Se puede modificar `recetas` desde cualquier pantalla?
- Respuesta: No directamente. `recetas` tiene `private set`, por lo que solo el `ViewModel` puede actualizarla mediante funciones controladas (como `agregarReceta`). Esto protege la consistencia del estado.
- Mostrar: `RecetaViewModel.kt` (`private set`, `agregarReceta`).

## Nivel C - Parte 2: Crear receta

### 16) Como se llega a crear receta?
- Respuesta: Desde `ListaRecetasScreen`, al presionar "Agregar receta", se ejecuta callback `onAgregarRecetaClick`, que en el nav graph hace `navController.navigate(AppRoutes.CrearReceta)`.
- Mostrar: `ListaRecetasScreen.kt` boton "Crear receta" y `AppNavGraph.kt`.

### 17) Que estado maneja `CrearRecetaScreen`?
- Respuesta: Usa estado local de UI: `nombreReceta` con `mutableStateOf` y `entradasIngredientes` con `mutableStateListOf`. Ese estado vive mientras la pantalla existe y alcanza para el formulario antes de guardar.
- Mostrar: `CrearRecetaScreen.kt` (declaraciones al inicio del composable).

### 18) Por que `entradasIngredientes` es una lista mutable de estado?
- Respuesta: Porque el formulario permite agregar y quitar filas dinamicamente. Con `mutableStateListOf`, Compose detecta cambios en la lista y redibuja automaticamente.
- Mostrar: `CrearRecetaScreen.kt` (`mutableStateListOf(...)`, `forEachIndexed`).

### 19) Como agregan una nueva fila de ingrediente?
- Respuesta: El boton "Agregar ingrediente" hace `entradasIngredientes.add(EntradaIngrediente("", "", "unidad"))`. Eso inserta una fila vacia con unidad por defecto.
- Mostrar: `CrearRecetaScreen.kt` boton correspondiente.

### 20) Como eliminan ingredientes y que restriccion tiene?
- Respuesta: En cada fila existe `onEliminarClick`. Solo elimina si `entradasIngredientes.size > 1`; asi se evita dejar la receta sin ninguna fila de ingrediente en el formulario.
- Mostrar: `CrearRecetaScreen.kt` bloque de `onEliminarClick`.

### 21) Donde esta el componente de fila de ingrediente?
- Respuesta: En `FilaEntradaIngrediente.kt`. Recibe valores y callbacks (`onNombreChange`, `onCantidadChange`, `onUnidadChange`, `onEliminarClick`) para que el estado real siga controlado por `CrearRecetaScreen`.
- Mostrar: `components/FilaEntradaIngrediente.kt`.

### 22) Como seleccionan la unidad del ingrediente?
- Respuesta: En la fila, el campo unidad es `readOnly`, al tocar se abre un `Dialog` con opciones (`unidad`, `kg`, `g`, etc.). Al elegir una opcion, se ejecuta `onUnidadChange(...)` y se cierra el dialogo.
- Mostrar: `FilaEntradaIngrediente.kt` (`Dialog`, lista `unidades`, `onUnidadChange`).

### 23) Como validan la cantidad numerica de los ingredientes agregados?
- Respuesta: Con `toDoubleOrNull()`. Si el texto no es numero, retorna `null`; el codigo lo transforma a `0.0` y despues valida `cantidad > 0`, por lo que entradas invalidas no se guardan.
- Mostrar: `CrearRecetaScreen.kt` bloque del boton "Guardar receta".

### 24) Que validan antes de llamar a `onSaveRecipe`?
- Respuesta: Dos condiciones: nombre de receta no vacio (`nombreReceta.isNotBlank()`) e ingredientes validos no vacios (`ingredientes.isNotEmpty()`). Si no se cumple, no intenta guardar.
- Mostrar: `CrearRecetaScreen.kt` condicion de guardado.

### 25) Quien guarda realmente la receta nueva?
- Respuesta: `AppNavGraph` recibe `onSaveRecipe(nombre, ingredientes)`, y ahi llama `recetaViewModel.agregarReceta(nombre, ingredientes)`. Luego hace `navController.popBackStack()` para volver a la lista.
- Mostrar: `AppNavGraph.kt` bloque de `composable(AppRoutes.CrearReceta)`.

### 26) Que hace `agregarReceta` por dentro?
- Respuesta: Limpia nombre con `trim()`, filtra ingredientes validos, corta si algo viene vacio, calcula `siguienteId` con `maxOfOrNull + 1`, construye `Receta` y actualiza la lista con `recetas = recetas + nuevaReceta`.
- Mostrar: `RecetaViewModel.kt` funcion `agregarReceta` completa.

### 27) Por que la receta aparece sola al volver a la lista?
- Respuesta: Porque `recetas` esta en `mutableStateOf`. Al cambiar su valor, Compose recompone las pantallas que leen ese estado (en este caso `ListaRecetasScreen`), entonces la UI se actualiza sin refresh manual.
- Mostrar: `RecetaViewModel.kt` y `ListaRecetasScreen.kt` (`viewModel.recetasFiltradas`).

## Nivel D - Preguntas de razonamiento (repreguntas tipicas)

### 28) Por que separar pantalla y componente (`ListaRecetasScreen` vs `TarjetaReceta`)?
- Respuesta: Es una separacion por responsabilidad. La pantalla coordina flujo y estado visual global; el componente se enfoca en como se dibuja una receta puntual. Esto facilita mantenimiento y pruebas.
- Mostrar: `ListaRecetasScreen.kt` + `TarjetaReceta.kt`.

### 29) Por que no guardan directamente en una base de datos?
- Respuesta: Porque para este practico el objetivo es dominar fundamentos de Compose, Navigation y ViewModel. Persistencia local puede venir despues. En esta fase, estado en memoria es suficiente para demostrar flujo funcional.
- Mostrar: `RecetaViewModel.kt` (estado en memoria).

### 30) Cual es la diferencia entre estado de UI y estado de app aqui?
- Respuesta: Estado de UI local: datos temporales del formulario (`nombreReceta`, filas). Estado de app compartido: `recetas`, filtros y logica en `RecetaViewModel`. Esta separacion evita mezclar responsabilidades.
- Mostrar: `CrearRecetaScreen.kt` + `RecetaViewModel.kt`.

### 31) Que evita que se agregue una receta vacia?
- Respuesta: Doble validacion: primero en `CrearRecetaScreen` antes de llamar callback y luego en `RecetaViewModel.agregarReceta`. Si algo invalido se escapa de UI, `ViewModel` igual lo bloquea.
- Mostrar: `CrearRecetaScreen.kt` y `RecetaViewModel.kt`.

### 32) Que pasa si el usuario escribe espacios en el nombre?
- Respuesta: `trim()` elimina espacios al inicio/final y luego se valida `isBlank()`. Si el nombre queda vacio, no se guarda.
- Mostrar: `RecetaViewModel.kt` (`nombreLimpio = nombre.trim()`).

### 33) Como se generan IDs sin base de datos?
- Respuesta: Se toma el mayor ID actual con `maxOfOrNull { it.id }` y se suma 1. Si la lista esta vacia, arranca en 1 con `?: 0`.
- Mostrar: `RecetaViewModel.kt` (`siguienteId`).

### 34) Hay riesgo de IDs repetidos?
- Respuesta: En este contexto simple, no, porque todo corre localmente en memoria y las altas pasan por una sola funcion. En un escenario multiusuario o persistente, si haria falta una estrategia mas robusta (BD/autoincrement/UUID).
- Mostrar: `RecetaViewModel.kt`.

### 35) Por que `onSaveRecipe` se pasa como callback?
- Respuesta: Para desacoplar UI de logica de guardado. `CrearRecetaScreen` no conoce `ViewModel` directamente; solo dispara una accion. El nav graph decide que hacer con esos datos.
- Mostrar: `CrearRecetaScreen.kt` firma del composable + `AppNavGraph.kt` implementacion.

### 36) Cual es la ventaja de `private data class EntradaIngrediente`?
- Respuesta: Es un modelo temporal solo para la pantalla de crear receta. Al ser `private`, no ensucia el resto del modulo con un tipo que no se necesita fuera de ese archivo.
- Mostrar: `CrearRecetaScreen.kt` (al final del archivo).

### 37) Como explicarias recomposicion en este proyecto?
- Respuesta: Recompose significa volver a ejecutar composables cuando cambia estado observado. Aqui, al cambiar `recetas`, `busquedaNombre` o `filtroIngrediente`, Compose recalcula partes de la UI que dependen de esos valores y la pantalla se actualiza.
- Mostrar: `RecetaViewModel.kt` + usos en `ListaRecetasScreen.kt`.

### 38) Que pasaria si no usaran `remember` en `CrearRecetaScreen`?
- Respuesta: En cada recomposicion se reiniciarian `nombreReceta` y lista de ingredientes, perdiendo lo que el usuario escribio. `remember` conserva ese estado mientras la pantalla siga en composicion.
- Mostrar: `CrearRecetaScreen.kt` (`remember { mutableStateOf(...) }`).

### 39) Como justificas el uso de `ignoreCase = true`?
- Respuesta: Mejora usabilidad: el filtro encuentra coincidencias sin exigir misma capitalizacion. Es un detalle pequeno pero importante para experiencia de usuario.
- Mostrar: `RecetaViewModel.kt` (`contains(..., ignoreCase = true)`).

### 40) Donde aplicaron coherencia de nombres en espanol?
- Respuesta: En rutas, pantallas, componentes, modelos y viewmodels de las partes 1 y 2 (`ListaRecetas`, `CrearReceta`, `RecetaViewModel`, `TarjetaReceta`, `Ingrediente`, etc.). Eso mejora lectura y defensa oral para el equipo.
- Mostrar: nombres de archivos en estructura de `app/src/main/java/com/example/planificador_de_comidas/`.

## Nivel E - Preguntas tecnicas cortas (rapidas de aula)

### 41) Que hace `popBackStack()`?
- Respuesta: Vuelve a la pantalla anterior en el stack de navegacion.
- Mostrar: `AppNavGraph.kt`.

### 42) Que hace `mutableStateOf`?
- Respuesta: Crea estado observable por Compose; cuando cambia, provoca recomposicion.
- Mostrar: `RecetaViewModel.kt`.

### 43) Que hace `mutableStateListOf`?
- Respuesta: Lista observable para Compose; al agregar/quitar elementos, se actualiza UI.
- Mostrar: `CrearRecetaScreen.kt`.

### 44) Que hace `items(...)` dentro de `LazyColumn`?
- Respuesta: Itera una coleccion y renderiza un composable por elemento.
- Mostrar: `ListaRecetasScreen.kt`.

### 45) Que hace `data class` aqui?
- Respuesta: Modela datos de dominio con sintaxis simple y utilidades automaticas (`copy`, `equals`, etc.).
- Mostrar: `Receta.kt` y `Ingrediente.kt`.

### 46) Que hace `trim()` en guardado?
- Respuesta: Limpia espacios sobrantes para evitar guardar nombres con formato incorrecto.
- Mostrar: `RecetaViewModel.kt`.

### 47) Que hace `toDoubleOrNull()`?
- Respuesta: Intenta convertir texto a numero decimal; si falla devuelve `null` en vez de romper la app.
- Mostrar: `CrearRecetaScreen.kt`.

### 48) Que hace `any { ... }` en filtro de ingredientes?
- Respuesta: Devuelve `true` si al menos un ingrediente cumple la condicion de busqueda.
- Mostrar: `RecetaViewModel.kt`.

## 3) Simbolos completos que debes memorizar (con explicacion)

### Flujo principal
- `MainActivity.onCreate`: punto de entrada Android; monta Compose.
- `setContent { ... }`: inicio del arbol de UI Compose.
- `viewModel<RecetaViewModel>()`: obtiene/crea el `ViewModel` de recetas.
- `AppNavGraph(recetaViewModel = recetaViewModel)`: conecta navegacion y estado compartido.

### Navegacion
- `rememberNavController()`: controlador de rutas.
- `NavHost(...)`: contenedor de destinos.
- `startDestination = AppRoutes.ListaRecetas`: primera pantalla.
- `composable(AppRoutes.ListaRecetas)`: destino de lista.
- `composable(AppRoutes.CrearReceta)`: destino de crear receta.
- `navController.navigate(AppRoutes.CrearReceta)`: ir a crear receta.
- `navController.popBackStack()`: volver atras.

### Estado y logica
- `var recetas by mutableStateOf(...)`: lista reactiva principal.
- `var busquedaNombre by mutableStateOf("")`: texto de busqueda.
- `var filtroIngrediente by mutableStateOf("")`: texto de filtro.
- `val recetasFiltradas get() = ...`: lista derivada por filtros.
- `actualizarBusquedaNombre(texto)`: modifica busqueda.
- `actualizarFiltroIngrediente(texto)`: modifica filtro.
- `agregarReceta(nombre, ingredientes)`: valida y agrega receta.

### UI lista
- `ListaRecetasScreen(...)`: pantalla principal de partes 1 y 2.
- `OutlinedTextField(...)`: inputs de busqueda/filtro.
- `LazyColumn { items(...) }`: render de recetas.
- `TarjetaReceta(receta)`: componente visual de cada item.

### UI crear receta
- `CrearRecetaScreen(...)`: formulario de alta.
- `remember { mutableStateOf("") }`: estado local del nombre.
- `remember { mutableStateListOf(...) }`: estado local de filas ingrediente.
- `FilaEntradaIngrediente(...)`: fila reutilizable del ingrediente.
- `EntradaIngrediente`: modelo temporal del formulario.
- `toDoubleOrNull()`: conversion segura de cantidad.

### Modelos
- `data class Receta(id, nombre, ingredientes)`: entidad receta.
- `data class Ingrediente(nombre, cantidad, unidad)`: entidad ingrediente.

## 4) Mini simulacro oral (10 preguntas con respuesta esperada)

### 1) Explicame en 20 segundos como viaja el dato al crear receta.
- Respuesta esperada: Escribo en `CrearRecetaScreen`, valido, llamo `onSaveRecipe`, en `AppNavGraph` se redirige a `recetaViewModel.agregarReceta`, cambia `recetas`, vuelvo con `popBackStack`, y `ListaRecetasScreen` se recompone mostrando la nueva receta.

### 2) Donde mostrarias que usan estado reactivo?
- Respuesta esperada: En `RecetaViewModel.kt` (`mutableStateOf` de `recetas`, `busquedaNombre`, `filtroIngrediente`) y en `CrearRecetaScreen.kt` (`mutableStateOf` y `mutableStateListOf`).

### 3) Cual es la validacion minima para guardar?
- Respuesta esperada: Nombre no vacio, ingredientes validos no vacios y cantidad mayor que cero.

### 4) Como se evita que se rompa al poner texto en cantidad?
- Respuesta esperada: `toDoubleOrNull()` evita excepcion; si falla toma `0.0` y luego no pasa validacion.

### 5) Por que no hacen logica pesada dentro del composable?
- Respuesta esperada: Porque la logica de negocio va en `ViewModel`; el composable se enfoca en UI e interacciones.

### 6) Como demostras que aplicaron componentes reutilizables?
- Respuesta esperada: `TarjetaReceta` para items de lista y `FilaEntradaIngrediente` para cada ingrediente en formulario.

### 7) Si te piden "donde esta la navegacion", que archivo abris?
- Respuesta esperada: `AppNavGraph.kt` y `AppRoutes.kt`.

### 8) Si te piden "donde se guarda", que archivo abris?
- Respuesta esperada: `RecetaViewModel.kt`, funcion `agregarReceta`.

### 9) Si te piden "donde se arma la receta", que archivo abris?
- Respuesta esperada: `CrearRecetaScreen.kt`.

### 10) Si te piden "donde se visualiza la receta", que archivo abris?
- Respuesta esperada: `ListaRecetasScreen.kt` y `TarjetaReceta.kt`.

## 5) Script de apertura (30-40 segundos)

"Nuestro practico en las partes 1 y 2 esta hecho con Compose, Navigation y ViewModel. En `MainActivity` inicializamos `RecetaViewModel` y lo compartimos por `AppNavGraph`. En `ListaRecetasScreen` mostramos recetas, busqueda y filtro por ingrediente usando estado reactivo. En `CrearRecetaScreen` cargamos nombre e ingredientes dinamicos, validamos y guardamos con callback. El `AppNavGraph` conecta ese callback con `agregarReceta` del `ViewModel`, y al actualizar `recetas` Compose recompone automaticamente la lista cuando volvemos."

## 6) Estrategia rapida de estudio (hoy)

1. Memoriza el flujo de 8 pasos de la seccion 0.
2. Practica respuestas 1-15 (base + lista recetas).
3. Practica respuestas 16-27 (crear receta + guardado).
4. Repite 10 preguntas de simulacro sin mirar apuntes.
5. Cierra explicando 5 simbolos clave: `NavHost`, `recetasFiltradas`, `agregarReceta`, `mutableStateOf`, `toDoubleOrNull`.
