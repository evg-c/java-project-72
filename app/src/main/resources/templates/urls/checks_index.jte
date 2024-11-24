@import gg.jte.Content
@import hexlet.code.NamedRoutes
@import hexlet.code.dto.UrlCheckPage
@param UrlCheckPage page
@param Content content

@if(page != null && page.getFlash() != null)
    <div class="alert alert-success" role="alert">
        <p>${page.getFlash()}</p>
    </div>
@endif

@template.layout.page(
    content = @`
    <section>
        <div class="container-lg mt-5">
            <h1>Сайт: ${page.getNameUrl()}</h1>
            <table class="table table-bordered table-hover mt-3">
                <tbody>
                    <tr>
                        <td>ID</td>
                        <td>${page.getIdUrl()}</td>
                    </tr>
                    <tr>
                        <td>Имя</td>
                        <td>${page.getNameUrl()}</td>
                    </tr>
                    <tr>
                        <td>Дата создания</td>
                        <td>
              ${String.valueOf(page.getCreatedUrl().toLocalDateTime().withSecond(0).withNano(0)).replaceFirst("T"," ")}
                        </td>
                    </tr>
                </tbody>
            </table>
            <h2 class="mt-5">Проверки</h2>
            <form action="${NamedRoutes.urlCheck(page.getIdUrl())}" method="post">
                <button type="submit" class="btn btn-primary">Запустить проверку
                </button>
            </form>
            <table class="table table-bordered table-hover mt-3">
                <thead>
                    <tr>
                        <th class="col-1">ID</th>
                        <th class="col-1">Код ответа</th>
                        <th>title</th>
                        <th>h1</th>
                        <th>description</th>
                        <th class="col-2">Дата проверки</th>
                    </tr>
                </thead>
                <tbody>
                    @for(var check:page.getUrlCheck())
                        <tr>
                            <td>${check.getId()}</td>
                            <td>${check.getStatusCode()}</td>
                            <td>${check.getTitle()}</td>
                            <td>${check.getH1()}</td>
                            <td>${String.valueOf(check.getDescription())}</td>
                            <td>
             ${String.valueOf(check.getCreatedAt().toLocalDateTime().withSecond(0).withNano(0)).replaceFirst("T", " ")}
                            </td>
                        </tr>
                    @endfor
                </tbody>
            </table>
        </div>
    </section>

    `,
    footer = @`
        <footer class="footer border-top py-3 mt-5 bg-light">
            <div class="container-xl">
                <div class="text-center">
                    created by
                    <a href="https://evg-c.github.io" target="_blank">Evg</a>
                </div>
            </div>
        </footer>
    `
)