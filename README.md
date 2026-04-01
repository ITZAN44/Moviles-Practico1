# Recipe Planner Lite (Guia interna)

Este README es para guiarnos mientras desarrollamos el practico.

## Alcance actual

- Hecho: Pantalla 1 (Lista de recetas)
- Hecho: Pantalla 2 (Crear receta)
- No hecho: Pantalla 3 (Plan semanal)
- No hecho: Pantalla 4 (Lista de compras)

## Reglas del proyecto

- Todo en memoria
- `ViewModel` como fuente unica de estado
- Estructura de carpetas por responsabilidad

## Estructura usada

- `app/src/main/java/com/example/planificador_de_comidas/data/model`
  - Modelos del dominio (`Recipe`, `Ingredient`)
- `app/src/main/java/com/example/planificador_de_comidas/presentation/viewmodel`
  - Estado global y logica simple de recetas
- `app/src/main/java/com/example/planificador_de_comidas/presentation/navigation`
  - Rutas y `NavHost`
- `app/src/main/java/com/example/planificador_de_comidas/presentation/screens`
  - Pantallas (`RecipeListScreen`, `CreateRecipeScreen`)
- `app/src/main/java/com/example/planificador_de_comidas/presentation/components`
  - Componentes reutilizables de UI

## Flujo

1. La app inicia en lista de recetas.
2. Se puede buscar por nombre y filtrar por ingrediente.
3. Con "Agregar receta" se abre el formulario.
4. Se agrega/elimina ingredientes dinamicamente.
5. Al guardar, vuelve a la lista y muestra la nueva receta.

## Nota

Si una receta no tiene nombre o no tiene ingredientes validos, no se guarda.

