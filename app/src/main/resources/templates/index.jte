@import hexlet.code.NamedRoutes
@import hexlet.code.dto.BuildUrlPage
@param BuildUrlPage page

@if(page != null && page.getFlash() != null)
    <div class="alert alert-danger" role="alert">
        <p>${page.getFlash()}</p>
    </div>
@endif

<!DOCTYPE html>
<html lang="ru">
    <head>
        <meta charset="UTF-8">
        <meta name="view-port" content="widht=device-width, initial-scale=1" />
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-QWTKZyjpPEjISv5WaRU9OFeRpok6YctnYmDr5pNlyT2bRjXh0JMhjY6hW+ALEwIH"
              crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.3/dist/js/bootstrap.bundle.min.js"
                integrity="sha384-YvpcrYf0tY3lHB60NNkmXc5s9fDVZLESaAA55NDzOxhy9GkcIdslK1eN7N6jIeHz"
                crossorigin="anonymous"></script>
        <title>Анализатор страниц</title>
    </head>
    <body>
        <main>
            <h1>Анализатор страниц</h1>
            <form action="${NamedRoutes.urlsPath()}" method="post">
                <div>
                    <label>
                        URL сайта
                        <input type="text" name="url" />
                    </label>
                </div>
                <input type="submit" value="Проверить" />
            </form>
        </main>
    </body>
</html>