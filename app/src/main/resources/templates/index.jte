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
        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.1.0/dist/css/bootstrap.min.css"
              rel="stylesheet"
              integrity="sha384-KyZXEAg3QhqLMpG8r+8fhAXLRk2vvoC2f3B09zVXn8CA5QIVfZOJ3BCsw2P0p/We"
              crossorigin="anonymous">
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