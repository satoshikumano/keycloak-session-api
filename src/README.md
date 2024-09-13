# これは何ですか？

KeycloakのAdmin Rest APIを拡張のサンプルです
ユーザーのセッションを人工的に作成するAPIを拡張しました。

`PUT /admin/realms/:realm_name/session/:user_name`

APIでサーバー上に指定したユーザーのセッションを人工的に作成します。
レスポンスでセッションに紐付くCookieの値を返却します。
(Keycloakで通常通りログインした場合に設定されるKEYCLOAK_IDENTITY Cookieの値と同様のものです。)

サンプルですのでエラー処理や認証処理は簡易的なものとなっています。
Adminの認証はかかっていますがAPIで任意のユーザーのセッションが作成出来てしまいますのでご注意ください。

# 使い方

## ビルド
```sh
mvn clean package
```

## インストール
Keycloakのproviderフォルダに生成されたjarファイルをコピーしてKeycloakを再起動してください。

```
cp target/keycloak-rest-example.jar {keycloakのフォルダ}/providers
```

## 動作確認用スクリプト

```
sh test.sh
```

## セッションを作成するClientの決定について

Client IDのprefixで決定します。これで都合が悪いケースではよしなに対応が必要です。
