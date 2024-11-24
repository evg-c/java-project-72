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
    <body class="d-flex flex-column min-vh-100">
        <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
            <div class="container-fluid">
                <a class="navbar-brand" href="/">Анализатор страниц</a>
                <div class="collapse navbar-collapse" id="navbarNav">
                    <div class="navbar-nav">
                        <a class="nav-link" href="/">Главная</a>
                        <a class="nav-link" href="/urls">Сайты</a>
                    </div>
                </div>
            </div>
        </nav>
        <main>
            <div class="container-fluid bg-dark p-5">
                <div class="row">
                    <div class="col-md-10 col-lg-8 mx-auto text-white">
                        <h1 class="display-3 mb-0">Анализатор страниц</h1>
                        <p class="lead">Бесплатно проверяйте сайты на SEO пригодность</p>
                        <form action="${NamedRoutes.urlsPath()}" method="post" class="rss-form text-body">
                            <div class="row">
                                <div class="col">
                                    <div class="form-floating">
                                        <input id="url-input" autofocus type="text" required name="url"
                                               aria-label="url"
                                               class="form-control w-100" placeholder="ссылка" autocomplete="off">
                                        <label for="url-input">Ссылка</label>
                                    </div>
                                </div>
                                <div class="col-auto">
                                    <button type="submit"
                                            class="h-100 btn btn-lg btn-primary px-sm-5">Проверить</button>
                                </div>
                            </div>
                        </form>
                        <p class="mt-2 mb-0 text-secondary">Пример: https://www.example.com</p>
                    </div>
                </div>
            </div>
        </main>
        <footer class="footer border-top py-3 mt-5 bg-light">
            <div class="container-xl">
                <div class="text-center">
                    created by
                    <a href="https://evg-c.github.io" target="_blank">Evg</a>
                </div>
            </div>
        </footer>
    </body>
</html>