@import gg.jte.Content
@import hexlet.code.dto.BasePage

@param Content content
@param Content footer
@param BasePage page = null

<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <meta name="view-port" content="width=device-width, initial-scale=1" />
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
        @if(page != null && page.getFlash() != null)
            <div class="alert alert-success" role="alert">
                <p>${page.getFlash()}</p>
            </div>
        @endif
        <div class="content">
            ${content}
        </div>
        @if(footer != null)
            <div class="footer">
                ${footer}
            </div>
        @endif
    </body>
</html>
